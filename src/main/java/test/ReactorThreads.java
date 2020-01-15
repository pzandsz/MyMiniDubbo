package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多线程的Reactor
 * 因为经典的Reactor模式中，所有的连接请求，I/O读写请求都在一个线程中，为了解决这个缺点，所以便引入了多线程来弥补缺陷
 *
 * 下列代码中，通过附加一个对读请求进行处理的对象，来模拟多线程的Reactor,实现了读写请求的处理和连接请求的处理不在一个线程中
 *
 *
 *
 * @Author: zengpeng
 * @Date: 2020/1/1416:32
 */
public class ReactorThreads {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();

        //设置为非阻塞
        server.configureBlocking(false);

        //监听8889接口
        server.bind(new InetSocketAddress(8889));

        //建立server和selector的联系
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            if(selector.selectNow() < 0){
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();

                //判断是否建立连接
                if(next.isAcceptable()){
                    ServerSocketChannel accept = (ServerSocketChannel) next.channel();
                    SocketChannel socketChannel = accept.accept();

                    socketChannel.configureBlocking(false);
                    SelectionKey register = socketChannel.register(selector, SelectionKey.OP_READ);

                    //为读请求附加一个 process(进程)
                    register.attach(new ReadProcess());

                    continue;
                }

                //处理读请求的线程和处理连接的线程不再是同一个线程
                if(next.isReadable()){
                    //获取附加的对象
                    ReadProcess process = (ReadProcess) next.attachment();
                    process.process(next);

                }

            }

        }
    }

}
