package test;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 嵌套
 * 一个监听事件内部可以嵌套监听事件，这样就形成递归的状态，从而实现异步式编程
 * Promise可以设置业务逻辑的返回值，从而实现业务逻辑的异步化,异步操作都使用到了Promise
 *
 *
 *
 *
 * @Author: zengpeng
 * @Date: 2020/1/1310:06
 */
public class PromiseTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        PromiseTest test = new PromiseTest();
        Promise<String> promise = test.search("Netty In Action");
        promise.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                System.out.println("Listener 1, make a notifice to Hong,price is " + future.get());
            }

        });
        promise.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                Promise<String> promise = test.search("Netty In Action A");
                String result = promise.get();
                System.out.println("Second: " + result);

                promise.addListener(new GenericFutureListener<Future<? super String>>() {
                    @Override
                    public void operationComplete(Future<? super String> future) throws Exception {
                        System.out.println("Third 3, make a notifice to Hong,price is " + future.get());
                    }

                });
            }

        });

        String result = promise.get();
        System.out.println("price is " + result);
    }

    private Promise<String> search(String prod) {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        // 创建一个DefaultPromise并返回
        DefaultPromise<String> promise = new DefaultPromise<String>(loop.next());
        loop.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(String.format("	>>search price of %s from internet!",prod));
                    Thread.sleep(5000);
                    promise.setSuccess("$19.99");// 等待5S后设置future为成功，
//                     promise.setFailure(new NullPointerException()); //当然，也可以设置失败
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },0, TimeUnit.SECONDS);

        return promise;
    }
}
