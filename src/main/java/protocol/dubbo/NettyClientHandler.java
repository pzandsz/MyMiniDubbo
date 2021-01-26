package protocol.dubbo;

import framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;


/**
 * Implements ChannelInboundHandlerAdapter
 * @author 
 */
@Slf4j
public class NettyClientHandler<T> extends ChannelInboundHandlerAdapter{
    private ChannelHandlerContext context;
    private T result;
    private Invocation invocation;


    /**
     * While channel active , invoke this method
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        log.info("channel active success , ctx:{}",ctx);
        context = ctx;
        context.writeAndFlush(invocation);
    }

    /**
     * Wait until wake up signal
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) {
        result = (T) msg;
    }


    void setPara(Invocation invocation) {
        this.invocation = invocation;
    }

    public T getResult() {
        return result;
    }
}
