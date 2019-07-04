package consumer;

import framework.Invocation;
import protocol.http.HttpClient;
import provider.api.HelloService;

/**
 * 服务消费方
 * @author 曾鹏
 */
public class Consumer {

    public static void main(String[] args) {

        //获得一个HttpClient对象
        HttpClient httpClient=new HttpClient();
        //发送消息
        Invocation invocation=new Invocation(HelloService.class.getName(),"sayHello",new Class[]{
                String.class
        },new Object[]{
                "welcome to my dubbo example...."
        });
        String result =httpClient.send("localhost", 8080, invocation);

        System.out.println(result);
    }
}
