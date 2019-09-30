package consumer;

import framework.ProxyFactory;
import provider.api.HelloService;

/**
 * 服务消费方
 * @author 曾鹏
 */
public class Consumer {

    public static void main(String[] args) {
        /**
         * 通过使用代理对象，简化main方法体
         */
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);


        System.out.println(helloService.sayHello("你好啊"));




    }
}
