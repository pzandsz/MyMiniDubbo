package protocol.dubbo;

import framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;



/**
 * handler处理器
 * 继承ChannelInboundHandlerAdapter
 * @author 曾鹏
 *
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter{
    /**
     * handler上下文
     */
    private ChannelHandlerContext context;
    private String result;
    private Invocation invocation;


    /**
     * 当通道状态为active时，表示已连接
     * @param ctx
     * @throws InterruptedException
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        System.out.println("成功建立连接!!!");
        Thread.sleep(1000);
        context=ctx;
        System.out.println("开始想服务端发送数据....");
        Thread.sleep(1000);
        context.writeAndFlush(invocation);
        System.out.println("发送完毕！");


    }

    /**
     * 收到服务端数据，唤醒等待线程
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        Thread.sleep(1000);
        result = (String) msg;
        System.out.println("接受服务端返回的执行结果,result: "+result);
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
