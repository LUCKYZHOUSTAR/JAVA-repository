package interview.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/4 19:40
 * @Description:
 */
public class NioClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);


        //提供服务器端的 ip 和 端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666); //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
        }
//...如果连接成功，就发送数据
        String str = "hello, 尚硅谷~";
//Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes()); //发送数据，将 buffer 数据写入 channel socketChannel.write(buffer);
        System.in.read();

    }
}
