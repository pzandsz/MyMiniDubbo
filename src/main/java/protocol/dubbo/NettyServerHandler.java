package protocol.dubbo;

import framework.Invocation;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import provider.LocalRegister;

import java.lang.reflect.Method;

/**
 * 继承ChannelInboundHandlerAdapter
 * @author 曾鹏
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 将数据注册到注册中心(写入文本文件)
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation= (Invocation) msg;

        System.out.println("server: msg==>"+msg);
        //获得Class
        Class serviceImpl= LocalRegister.get(invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamTypes());

        //通过反射调用方法
        Object result=method.invoke(serviceImpl.newInstance(),invocation.getParams());
        System.out.println("Netty---------"+result.toString());
        ctx.write(result);

    }

    /**
     * 服务器端读到网络数据后的处理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //flush掉所有数据
        //当flush完成后才能后关闭连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
}
