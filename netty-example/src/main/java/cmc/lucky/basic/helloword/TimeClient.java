package cmc.lucky.basic.helloword;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:24 2017/10/23
 */
public class TimeClient {
    public void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel arg0)
                                throws Exception {
                            System.out.println("client initChannel..");
                            arg0.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            // 发起异步连接操作
            //这里发生了粘包，拆包的操作
            ChannelFuture f = b.connect(host, port).sync();
            byte[] req = "我又发送了一次信息".getBytes();
            ByteBuf firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
            Object obj = f.channel().writeAndFlush(firstMessage).get(30L, TimeUnit.SECONDS);
            ByteBuf buf = (ByteBuf) obj;
            byte[] req2 = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req2, "UTF-8");
            System.out.println("Now is :" + body);
            // 等待客户端链路关闭
//            f.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        int port = 9000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
            }
        }
        new TimeClient().connect(port, "127.0.0.1");
    }
}
