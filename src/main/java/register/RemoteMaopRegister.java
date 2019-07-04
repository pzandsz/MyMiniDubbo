package register;

import framework.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于存储远程信息
 * @author 曾鹏
 */
public class RemoteMaopRegister {
    private static Map<String,List<URL>> REGISTER=new HashMap<String, List<URL>>();

    /**
     * 注册
     * @param interfaceName
     * @param url
     */
    public static void register(String interfaceName, URL url){
        List<URL> urls = Collections.singletonList(url);
        REGISTER.put(interfaceName,urls);
    }
}
