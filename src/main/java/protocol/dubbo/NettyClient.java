package protocol.dubbo;

import framework.Invocation;
import framework.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * 基于netty的dubbo协议
 * 对netty还不够熟悉，无法继续向下写
 * @author 曾鹏
 */
public class NettyClient {

    private final String host;
    private final int port;

    private Invocation invocation;

    public NettyClient(String host,int port,Invocation invocation){
        this.host = host;
        this.port = port;
        this.invocation=invocation;
    }

    public String send() throws Exception{
//        System.out.println("4.NettyClient--->开始send");
        Object result;

        EventLoopGroup group = new NioEventLoopGroup();

        NettyClientHandler nettyClientHandler = new NettyClientHandler();
        nettyClientHandler.setPara(invocation);
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception{
                            ch.pipeline().addLast(
                                    new ObjectDecoder(1024, ClassResolvers
                                            .cacheDisabled(this.getClass().getClassLoader())));

                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(nettyClientHandler);
                        }
                    });

            ChannelFuture future = bootstrap.connect().sync();

            future.channel().closeFuture().sync();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return nettyClientHandler.getResult();
    }
}
