package cmc.lucky.test.reconneted2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.*;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:29 2018/3/1
 */
public class ClientHandler extends ChannelInboundHandlerAdapter implements TimerTask {
    protected final HashedWheelTimer timer = new HashedWheelTimer(new DefaultThreadFactory("connector.timer"));

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
            CharsetUtil.UTF_8));
    //心跳次数
    private static final int RETRY_TIME = 3;


    private Bootstrap bootstrap;

    public ClientHandler(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    private static int attmpts;
    private int currentTime = 0;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("激活时间是:" + new Date());
        System.out.println("client channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭时间是:" + new Date());
        System.out.println("client channelInActive");

        if (attmpts < 12) {
            attmpts++;
        }
        long timeOut = 2 << attmpts;
        timer.newTimeout(this, timeOut, TimeUnit.MILLISECONDS);

        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        System.out.println(message);
        if (message.equals("Heartbeat")) {
            ctx.write("has read message from server");
            ctx.flush();
        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        System.out.println("触发空闲时间：" + new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            //客户端某段时间内，没有写操作
            if (event.state() == IdleState.WRITER_IDLE) {
                if (currentTime <= RETRY_TIME) {
                    currentTime++;
                    System.out.println("currentTime:" + currentTime);
                    ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE);
                }

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常" + cause.toString());
        ctx.close();
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        ChannelFuture future = null;
        //这块应该加锁，防止多个线程同时操作
        bootstrap
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS))
                                .addLast("decoder", new StringDecoder())
                                .addLast("encoder", new StringEncoder())
                                .addLast(new ClientHandler(bootstrap));
                    }
                });

        future = bootstrap.connect("127.0.0.1", 8080);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                boolean succeed = future.isSuccess();
                if (!succeed) {
                    future.channel().pipeline().fireChannelInactive();
                }
            }
        });

    }
}
