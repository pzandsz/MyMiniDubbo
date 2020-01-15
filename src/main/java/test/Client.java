package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: zengpeng
 * @Date: 2020/1/1415:01
 */
public class Client {

    private SocketChannel socketChannel;

    public Client() throws IOException {
        socketChannel = SocketChannel.open();
        //连接127.0.0.1:8888
        socketChannel.connect(new InetSocketAddress("localhost",8889));
    }

    /**
     * 从通道中读取信息
     * @throws IOException
     */
    public void readMsg() throws IOException {
        //需要事先分配缓存大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*12);

        //将信息读取到byteBuffer中
        socketChannel.read(byteBuffer);

        //调整指针位置，指向开始位置
        byteBuffer.flip();

        byte[] array = byteBuffer.array();
        System.out.println("读得的数据是: " + new String(array));

        byteBuffer.clear();
    }


    /**
     * 发送信息
     * @param message
     */
    public void sendMsg(String message) throws IOException {

        //write方法不会触发channel对应的selectionKey的变化
        socketChannel.write(ByteBuffer.wrap(message.getBytes()));
    }


    public static void main(String[] args) throws IOException, InterruptedException {


        System.out.println("开始发消息");
        for(int i=0 ; i<10 ; i++){
            Client client = new Client();
            client.sendMsg("客户端发给服务端，adad");
        }

//        System.out.println("-----------");
//        client.readMsg();

        Thread.sleep(10000);
    }

}
