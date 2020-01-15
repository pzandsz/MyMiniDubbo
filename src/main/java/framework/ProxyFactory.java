package framework;

import protocol.dubbo.NettyClient;
import register.RemoteMapRegister;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂
 * @author 曾鹏
 */
public class ProxyFactory {
    public static <T> T getProxy(final Class interfaceClass){

        /**
         * 使用jdk的动态代理,根据传入的类创建一个代理对象并返回
         * loader：一个ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载
         * interfaces:一个Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，
         *      如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组
         *      接口中的方法了
         * h:一个InvocationHandler对象，表示的是当我这个动态代理对象在调用方法的时候，
         *      会关联到哪一个InvocationHandler对象上
         */
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                new InvocationHandler() {
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
//                System.out.println("1.invoke方法调用...");
                //通过网络获得一个HttpClient对象
                //HttpClient httpClient=new HttpClient();


                //1.发送给服务提供者的信息，服务提供方将通过这些信息和反射机制来执行方法并返回结果
                Invocation invocation=new Invocation(interfaceClass.getName(),method.getName(),
                        method.getParameterTypes(),args);

//                System.out.println("2.invocation:"+invocation.toString());

                /**
                 * 通过接口名称向注册中心获得消息发送的地址
                 * 实际上在使用zookeeper作为远程注册中心时，地址信息会写在配置文件中
                 */
                URL url= RemoteMapRegister.random(interfaceClass.getName());

//                System.out.println("3.url:"+url);

                /**
                 * 向指定的地址发送请求并获得响应
                 */
                NettyClient nettyClient=new NettyClient(url.getHostname(),
                        url.getPort(),invocation);

//                String result =httpClient.send(url.getHostname(), url.getPort(), invocation);

                String result=nettyClient.send();
                return result;

            }
        });

    }
}
