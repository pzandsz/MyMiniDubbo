package test;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程Reactor模式下，附加的读请求对象
 * @Author: zengpeng
 * @Date: 2020/1/1416:56
 */
public class ReadProcess {
    private static final ExecutorService executor = Executors.newFixedThreadPool(16);

    public void process(SelectionKey selectionKey){
        executor.submit(()->{

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel channel = (SocketChannel) selectionKey.channel();

            int count = channel.read(buffer);
            if (count < 0) {
                channel.close();
                selectionKey.cancel();
                return null;
            } else if(count == 0) {
                return null;
            }

            buffer.flip();
            System.out.println("读到的信息: " + new String(buffer.array()));
            System.out.println(Thread.currentThread().getName() + " ReadProcess...");
            return null;

        });
    }
}
