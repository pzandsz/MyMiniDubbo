package test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Netty的线程池
 * @Author: zengpeng
 * @Date: 2020/1/1311:03
 */
public class EventGroupLoopTest {

    /**
     * Netty的事件循环机制有两个基本接口:EventLoop 和 EventLoopGroup
     * EventLoop是事件循环, 每个EventLoop被包装成一个Task放到线程池中运行,但其本身也可以看做为一个线程池,
     * 如Nio的事件循环会不断select后获取任务并执行,Nio的事件循环在实现的时候使用死循环的方式不断select,
     * 然后处理提交给EventLoop的系统任务，因此可以看做是一个线程池
     *
     *
     * EventLoopGroup是多个事件循环形成的组,EventLoopGroup作为线程池组,线程池组的意义是采用给的策略选取一个EventLoop并提交任务
     *
     * @param args
     */

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        //设置事件循环组
        bootstrap.group(bossGroup,workGroup);


    }
}
