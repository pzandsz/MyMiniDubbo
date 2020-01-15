package xml.util;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 用于对文本文件的读写
 * @Author: zengpeng
 * @Date: 2019/10/916:12
 */
public class FileWriteReadUtil {


    /**
     * 读取文件中的敏感词列表
     * @return
     */
    public static HashSet<String> getSensesitive(){

        HashSet<String> sensetiveWordSet = new HashSet<>();

        File file=new File("src/main/java/sensetiveWord.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                sensetiveWordSet.add(tempString);
            }


            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return sensetiveWordSet;
    }


    public static void main(String[] args) {
        System.out.println(FileWriteReadUtil.getSensesitive());
    }
}
