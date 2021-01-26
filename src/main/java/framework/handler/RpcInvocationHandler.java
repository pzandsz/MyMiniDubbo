package framework.handler;

import framework.Invocation;
import framework.URL;
import lombok.extern.slf4j.Slf4j;
import protocol.dubbo.NettyClient;
import register.RemoteMapRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RpcInvocationHandler implements InvocationHandler {

    private String interfaceName;

    public RpcInvocationHandler() {
    }

    public RpcInvocationHandler(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     *  代理执行方法
     * @param proxy：代理的真实对象
     * @param method：代理的真实对象的某个方法的method对象
     * @param args:代理的真实对象的某个方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("interfaceName:{" + interfaceName + "}");
        //发送给服务提供者的信息，服务提供方将通过这些信息和反射机制来执行方法并返回结果
        Invocation invocation = new Invocation(interfaceName,method.getName(),
                method.getParameterTypes(),args);

        /**
         * 通过接口名称向注册中心获得消息发送的地址
         * 实际上在使用zookeeper作为远程注册中心时，地址信息会写在配置文件中
         */
        URL url= RemoteMapRegister.random(interfaceName);
        /**
         * 向指定的地址发送请求并获得响应
         */
        NettyClient nettyClient = new NettyClient(url.getHostname(),
                url.getPort(),invocation);

//                String result =httpClient.send(url.getHostname(), url.getPort(), invocation);

        String result = nettyClient.send();
        return result;
    }
}
