package interview.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/4 19:27
 * @Description:
 */
public class NonBlockServer {

    public static void main(String[] args) throws Exception {


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));


        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

//把 serverSocketChannel 注册到 selector 关心 事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {

//这里我们等待 1 秒，如果没有事件发生, 返回
            if (selector.select(1000) == 0) { //没有事件发生
                System.out.println("服务器等待了 1 秒，无连接");
                continue;
            }


            //如果返回的>0, 就获取到相关的 selectionKey 集合 //1.如果返回的>0， 表示已经获取到关注的事件
            //2. selector.selectedKeys() 返回关注事件的集合
            // 通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

//根据 key 对应的通道发生的事件做相应处理
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();


            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
//如果是 OP_ACCEPT, 有新的客户端连接
                if (key.isAcceptable()) {

                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println(" 客 户 端 连 接 成 功 生 成 了 一 个 socketChannel " +
                            socketChannel.hashCode());


                    //设置为非阻塞的
                    socketChannel.configureBlocking(false);
//将 socketChannel 注册到 selector, 关注事件为 OP_READ， 同时给 socketChannel //关联一个 Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }


                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();

                    channel.read(buffer);
                    System.out.println("form 客户端 " + new String(buffer.array()));

                }

                //手动从集合中移动当前的 selectionKey, 防止重复操作
                keyIterator.remove();
            }


        }


    }
}
