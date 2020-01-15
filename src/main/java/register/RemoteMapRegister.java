package register;

import com.sun.org.apache.regexp.internal.RE;
import framework.URL;

import java.io.*;
import java.util.*;

/**
 * 通过存储在本地文件的方式模拟 存储远程信息
 * @author 曾鹏
 */
public class RemoteMapRegister {
    private static Map<String,List<URL>> REGISTER=new HashMap<String, List<URL>>();

    private  final static String PATH="D://MyMiniDubbo/register.txt";

    /**
     * 注册
     * 以接口的全路径类名来作为key,value存储套接字信息，
     * 服务提供者将接口名称和套接字信息作为KV存储的map中
     * 服务消费者通过接口名称获得套接字信息，并向套接字中指定的地址发起远程调用请求
     * @param interfaceName
     * @param url
     */
    public static void register(String interfaceName, URL url) throws IOException {
        List<URL> urls = Collections.singletonList(url);
        REGISTER.put(interfaceName,urls);

        //写入文本
        saveFile();
    }


    public static Class get(String interfaceName){
        //1.读取文件
        Map<String, List<URL>> file = getFile();
        //2.返回Class文件信息
        return file.get(interfaceName).getClass();
    }

    /**
     * 模拟一个随机的负载均衡策略(完全随机，没有根据权重之类的进行随机)
     * @param interfaceName
     * @return
     */
    public static URL random(String interfaceName){
        REGISTER=getFile();
        if(REGISTER!=null){
            Random random=new Random();
            List<URL> urls = REGISTER.get(interfaceName);
            int n=random.nextInt(urls.size());
            return urls.get(n);
        }
        return null;
    }

    /**
     * 因为consumer和provider是两个线程,REGISTER不能共享，所以借助文本实现注册中心
     * 以文本形式实现注册中心
     * 写入文本
     */
    public static void saveFile() throws IOException {
        ObjectOutputStream obs=null;
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(PATH);
            //包装成ObjectOutputStream
            obs =new ObjectOutputStream(fileOutputStream);
            //将map对象存入文件中
            obs.writeObject(REGISTER);
            obs.flush();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            obs.close();
        }

    }

    /**
     * 获取文本
     */
    public static Map<String,List<URL>> getFile(){

        try {
            FileInputStream fileInputStream = new FileInputStream(PATH);
            ObjectInputStream ois=new ObjectInputStream(fileInputStream);

             return  (Map<String, List<URL>>) ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;

    }
}