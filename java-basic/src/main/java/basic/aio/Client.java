package basic.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:26 2018/3/1
 */
public class Client {


    private AsynchronousChannelGroup group;

    private String host;
    private int port;
    AsynchronousSocketChannel client;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void initGroup() {
        if (group == null) {
            try {
                group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(5), 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        initGroup();
        read();
    }


    //read的操作逻辑操作
    private void read() {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        client.read(byteBuffer, this, new CompletionHandler<Integer, Client>() {
            @Override
            public void completed(Integer result, Client attachment) {
                System.out.println(result);
                System.out.println(Thread.currentThread().getName() + " client read data: " + new String(byteBuffer.array()));
                try {
                    byteBuffer.clear();
//                    if (client != null) client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    read();
                }
            }

            @Override
            public void failed(Throwable exc, Client attachment) {
                System.out.println("read faield");
            }
        });
    }


    private void send(String smg) {
        client.write(ByteBuffer.wrap(smg.getBytes()));
    }

    private void send() {
        try {
            //复用一个group
            client = AsynchronousSocketChannel.open(group);
            client.connect(new InetSocketAddress(host, port)).get();
        } catch (Exception E) {
            System.out.println(E);
        }
    }


    public static void main(String[] args) {
        try {

            Client client = new Client("127.0.0.1", 8989);
            client.send();
            client.send("haha");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
