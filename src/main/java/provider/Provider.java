package provider;

import framework.URL;
import protocol.http.HttpServer;
import provider.api.HelloService;
import provider.imp.HelloServiceImp;
import register.RemoteMaopRegister;

import java.util.Map;

/**
 * 服务启动类
 * @author 曾鹏
 */
public class Provider {

    public static void main(String[] args) {
        //1.本地注册{服务名：实现类}
        LocalRegister.register(HelloService.class.getName(), HelloServiceImp.class);

        //2.远程注册{服务名:List<URL>}
        URL url=new URL("localhost",8080);
        RemoteMaopRegister.register(HelloService.class.getName(),url);

        //3.启动tomcat
        HttpServer httpServer=new HttpServer();
        httpServer.start("localhost",8080);
    }
}
