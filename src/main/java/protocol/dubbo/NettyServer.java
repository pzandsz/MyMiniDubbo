package protocol.dubbo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


import java.net.InetSocketAddress;

/**
 * 基于netty的dubbo协议
 * @author 曾鹏
 */
public class NettyServer {

    private ServerBootstrap bootstrap;
    private NioEventLoopGroup eventLoopGroup;

    /**
     * 开启tomcat
     * @param hostName
     * @param port
     */
    public void start(String hostName, int port){

        try {
            bootstrap = new ServerBootstrap();
            eventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().
                                    addLast(new ObjectDecoder(1024*1024,
                                            ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });

            bootstrap.bind().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
