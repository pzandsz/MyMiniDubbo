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
         * 通过制定Class,创建代理对象，代理对象会负责将数据发送到服务提供方
         *
         */
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);

        System.out.println(helloService.sayHello("你好啊"));
    }
}
