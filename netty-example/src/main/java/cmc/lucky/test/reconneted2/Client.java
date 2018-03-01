package cmc.lucky.test.reconneted2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:29 2018/3/1
 */
public class Client {


    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        ChannelFuture future = null;

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new ClientHandler(b));
                        }
                    });

            future = b.connect(host, port).sync();
            //阻塞直到关闭
//            future.channel().closeFuture().sync();

        } finally {
//            if (null != future) {
//                if (future.channel() != null && future.channel().isOpen()) {
//                    future.channel().close();
//                }
//            }
//
//            System.out.println("服务端把我给关闭了，准备重连操作");
//            connect(port, host);
//            System.out.println("服务端把我给关闭了，重连成功");
//        }
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new Client().connect(port, "127.0.0.1");

    }
}

