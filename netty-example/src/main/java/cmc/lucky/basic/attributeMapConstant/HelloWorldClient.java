package cmc.lucky.basic.attributeMapConstant;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author:chaoqiang.zhou
 * @Description:服务端新进行解码操作，然后进行编码发出去
 * 客户端先进性编码操作发出去，然后解码读取服务端的信息
 * @Date:Create in 11:30 2017/11/27
 */
public class HelloWorldClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "9090"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));


    public static void main(String[] args) throws Exception{
        initChannel();
    }
    public static void initChannel() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            //解码
                            p.addLast("decoder", new StringDecoder());
                            //编码
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new HelloWorldClientHandler());
                            p.addLast(new HelloWorld2ClientHandler());

                        }
                    });
            ChannelFuture future = b.connect(HOST, PORT).sync();
            future.channel().writeAndFlush("hello netty,Test attributeMapp");
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
