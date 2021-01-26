package framework;

import framework.handler.RpcInvocationHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * 代理工厂
 * @author 曾鹏
 */
@Slf4j
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
                new RpcInvocationHandler(interfaceClass.getName()));

    }
}
