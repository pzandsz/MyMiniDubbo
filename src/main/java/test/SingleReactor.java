package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 单反应堆模式
 *
 * 在Reactor模式中，包含如下角色:
 *      Reactor:将I/O事件派发给对应的Handler
 *      Acceptor:处理客户端连接请求
 *      Handler:执行非阻塞 读/写 操作
 *
 * 缺点:所有的读写请求以及对新连接请求的处理都在同一个线程中处理，
 * 无法充分利用cpu的优势
 *
 *
 *
 * 关于EventLoop,EventLoop是一个事件循环，一个EventLoop可以对应下面的while(true){}循环体中代码，
 * 一个EventLoop就是一个循环监听channel的事件
 * @Author: zengpeng
 * @Date: 2020/1/14 14:25
 */
public class SingleReactor {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();

        //设置为非阻塞
        server.configureBlocking(false);

        //监听8888端口
        server.bind(new InetSocketAddress(8888));
        //10000
        server.register(selector, SelectionKey.OP_ACCEPT);


        //循环判断selector中各个channel的连接状态
        while (true){
            //select()是一个阻塞的方法，如果没有连接,则方法会阻塞在这里
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                System.out.println(next.readyOps());
                //问题1：channel的状态转换是如何进行的?

                //是否允许连接 第五位的标位是否为1
                if(next.isAcceptable()){
                    ServerSocketChannel accept = (ServerSocketChannel) next.channel();
                    SocketChannel socketChannel = accept.accept();

                    System.out.println("server: " + accept.getLocalAddress());
                    System.out.println("client: " + socketChannel.getRemoteAddress());

                    System.out.println("建立连接...");
                    //绑定新连接的channel和selector
                    socketChannel.configureBlocking(false);
                    //如果要绑定多种联系，可以使用 | 进行组合  channel是否可读，可写，需要在建立连接的时候确立绑定关系
                    socketChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE);


                    //写数据
                    socketChannel.write(ByteBuffer.wrap("hello".getBytes()));

                    continue;
                }

                if(next.isReadable()){
                    System.out.println("该通道语允许读取数据！");
                    SocketChannel socketChannel = (SocketChannel) next.channel();


                    ByteBuffer buffer = ByteBuffer.allocate(1024*12);

                    socketChannel.read(buffer);

                    //调整指针位置，指向开始位置
                    buffer.flip();
                    System.out.println("读到: " + new String(buffer.array()) );
                    continue;

                }
            }
        }

    }
}
