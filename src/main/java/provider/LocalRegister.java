package provider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册类 用于存储本地注册的信息
 * @author 曾鹏
 */
public class LocalRegister {
    private static Map<String,Class> map = new ConcurrentHashMap<String, Class>();
    public static void register(String interfaceName,Class implClass){
        map.put(interfaceName,implClass);
    }
    public static Class get(String interfaceName){
        return map.get(interfaceName);
    }
}
