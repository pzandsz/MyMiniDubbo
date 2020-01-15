package sensetive;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import xml.util.FileWriteReadUtil;

import java.util.*;

/**
 * 关键字过滤
 * @Author: zengpeng
 * @Date: 2019/10/914:56
 */

public class SensetiveWordFilter {


    private Map<String,String> sensitiveWordMap;

    /**
     * 将关键字set转换为有限状态机map
     */
    public Map<String,String> addSensitiveWordSetToMap(HashSet<String> sensitiveWordSet){

        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap<>(sensitiveWordSet.size());

        String key;
        Map nowMap;
        Map<String,String> newWordMap;

        //迭代senesitiveWordSet
        Iterator<String> iterator = sensitiveWordSet.iterator();

        while (iterator.hasNext()){
            key = iterator.next();

            //当前的Map
            nowMap = sensitiveWordMap;

            for(int i=0 ; i<key.length() ; i++){

                //转换为char型
                char keyChar = key.charAt(i);

                Object wordMap = nowMap.get(keyChar);

                if(wordMap != null){
                    nowMap = (Map) wordMap;
                }else{
                    //不存在时，则构建一个map,同时将isEnd设置为0,因为他不是最后一个
                    newWordMap = new HashMap<String, String>();

                    //不是最后一个
                    newWordMap.put("isEnd", "0");


                    //递归实现初始化
                    nowMap.put(keyChar, newWordMap);
                    nowMap = newWordMap;
                }

                if (i == key.length() - 1) {
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }


        return sensitiveWordMap;
    }


    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @return 若包含返回true，否则返回false
     */
    public boolean isContaintSensitiveWord(String txt) {

        // 全部小写,并去除所有的符号
//        txt = replaceChar(txt);

        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            //判断是否包含敏感字符
            int matchFlag = checkSensitiveWord(txt, i);

            //大于0存在，返回true  最小匹配原则
            if (matchFlag > 0) {
                flag = true;
                String strTemp = txt.substring(i,(i+matchFlag));
                System.out.println("strTemp: "+strTemp);


                return flag;
            }
        }
        return flag;
    }


    /**
     * 检查文字中是否包含敏感字符
     *
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    public  int checkSensitiveWord(String txt, int beginIndex) {
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;

        //匹配标识数默认为0
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;

        if(nowMap == null){
            //当没有带参数构造敏感词Map的情况下，从文件中读取
            nowMap = addSensitiveWordSetToMap(FileWriteReadUtil.getSensesitive());
        }

        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);

            //获取指定key
            nowMap = (Map) nowMap.get(word);

            //存在，则判断是否为最后一个
            if (nowMap != null) {
                //找到相应key，匹配标识+1
                matchFlag++;

                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;

                }
            } else {
                //不存在，直接返回
                break;
            }
        }
        //长度必须大于等于1，为词
        if (matchFlag < 1 || !flag) {
            matchFlag = 0;
        }

        System.out.println(matchFlag);
        return matchFlag;
    }

    public static void main(String[] args) {
        HashSet<String> set=new HashSet<>();

        set.add("咪咕爱看");
        set.add("小咪");
        set.add("小咕");
        set.add("小爱");
        set.add("小咪咕");
        set.add("咪咕");


        SensetiveWordFilter filter=new SensetiveWordFilter();
        filter.addSensitiveWordSetToMap(set);
        System.out.println(filter.isContaintSensitiveWord("呼叫小爱        "));
    }

}
