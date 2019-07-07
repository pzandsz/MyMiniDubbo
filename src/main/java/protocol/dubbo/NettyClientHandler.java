package protocol.dubbo;

import framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.util.concurrent.Callable;

/**
 * 继承ChannelInboundHandlerAdapter
 * @author 曾鹏
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter{
    private ChannelHandlerContext context;
    private String result;
    private Invocation invocation;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        context=ctx;
        System.out.println("发送数据");

        context.writeAndFlush(invocation);


    }

    /**
     * 收到服务端数据，唤醒等待线程
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) {
        result = (String) msg;
        System.out.println("result===>"+result);
    }

    /**
     * 写出数据，开始等待唤醒
     */



    void setPara(Invocation invocation) {
        this.invocation = invocation;
    }

    public String getResult() {
        return result;
    }
}
