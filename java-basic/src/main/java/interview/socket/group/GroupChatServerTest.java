//package interview.socket.group;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.*;
//import java.util.Iterator;
//
///**
// * @Auther: chaoqiang.zhou
// * @Date: 2019/12/4 19:51
// * @Description:
// */
//public class GroupChatServerTest {
//
//    private Selector selector;
//    private ServerSocketChannel serverSocketChannel;
//
//
//    private final int PORT = 6666;
//
//
//    public GroupChatServerTest() {
//
//        selector = Selector.open();
//
//        serverSocketChannel = ServerSocketChannel.open();
//
//
//        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
//
//        serverSocketChannel.configureBlocking(false);
//
//        serverSocketChannel.register(selector, SelectionKey.OP_READ);
//    }
//
//
//    public void listen() {
//        System.out.println("监听线程" + Thread.currentThread().getName());
//
//        try {
//
//
//            while (true) {
//                int count = selector.select();
//
//                if (count > 0) {
//                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//
//
//                    while (iterator.hasNext()) {
//                        SelectionKey key = iterator.next();
//
//                        if (key.isAcceptable()) {
//
//
//                            SocketChannel socketChannel = (SocketChannel) key.channel();
//                            socketChannel.configureBlocking(false);
//                            socketChannel.register(selector, SelectionKey.OP_READ);
//                        }
//
//
//                        if (key.isReadable()) {
//
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//
//        }
//    }
//
//    public void readData(SelectionKey key) {
//        SocketChannel socketChannel = null;
//
//        try {
//            socketChannel = (SocketChannel) key.channel();
//
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//
//            int count = socketChannel.read(byteBuffer);
//
//            if (count > 0) {
//                //把缓存区的数据转成字符串
//                String msg = new String(byteBuffer.array());
//                //输出该消息
//                System.out.println("form 客户端: " + msg);
//
//                //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
//                sendInfoToOtherClients(msg, socketChannel);
//            }
//
//        } catch (Exception e) {
//
//        }
//    }
//
//
//    //转发消息给其它客户(通道)
//    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
//
//        System.out.println("服务器转发消息中...");
//        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
//        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
//        for (SelectionKey key : selector.keys()) {
//
//            //通过 key  取出对应的 SocketChannel
//            Channel targetChannel = key.channel();
//
//            //排除自己
//            if (targetChannel instanceof SocketChannel && targetChannel != self) {
//
//                //转型
//                SocketChannel dest = (SocketChannel) targetChannel;
//                //将msg 存储到buffer
//                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
//                //将buffer 的数据写入 通道
//                dest.write(buffer);
//            }
//        }
//
//    }
//}
