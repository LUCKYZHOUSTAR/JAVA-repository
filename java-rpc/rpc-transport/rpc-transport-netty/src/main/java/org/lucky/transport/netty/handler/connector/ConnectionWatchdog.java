package org.lucky.transport.netty.handler.connector;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.jupiter.common.util.internal.logging.InternalLogger;
import org.jupiter.common.util.internal.logging.InternalLoggerFactory;
import org.lucky.transport.channel.LChannelGroup;
import org.lucky.transport.netty.channel.NettyChannel;
import org.lucky.transport.netty.handler.ChannelHandlerHolder;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:27 2018/2/8
 */
public abstract class ConnectionWatchdog extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ConnectionWatchdog.class);

    private static final int ST_STARTED = 1;
    private static final int ST_STOPPED = 2;

    private final Bootstrap bootstrap;

    private final Timer timer;
    private final SocketAddress remoteAddress;
    //一个服务对应多个channel，多个channel用一个group来进行管理操作
    private final LChannelGroup group;

    //状态标识
    private volatile int state = ST_STARTED;
    private int attempts;

    public ConnectionWatchdog(io.netty.bootstrap.Bootstrap bootstrap, Timer timer, SocketAddress remoteAddress, LChannelGroup group) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.remoteAddress = remoteAddress;
        this.group = group;
    }

    public void start() {
        state = ST_STARTED;
    }

    public void stop() {
        state = ST_STOPPED;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel ch = ctx.channel();
        if (group != null) {
            group.add(NettyChannel.attachChannel(ch));
        }

        attempts = 0;
        logger.info("Connects with {}", ch);
        //链式继续向下操作
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        boolean doReconnect = isReconnectNeeded();
        if (doReconnect) {
            if (attempts < 12) {
                attempts++;
            }
            long timeOut = 2 << attempts;
            timer.newTimeout(this, timeOut, TimeUnit.MILLISECONDS);
        }
        super.channelInactive(ctx);
    }

    public boolean isStarted() {
        return state == ST_STARTED;
    }


    @Override
    public void run(Timeout timeout) throws Exception {

        if (!isReconnectNeeded()) {
            logger.warn("Cancel reconnecting with {}.", remoteAddress);
            return;
        }

        final ChannelFuture future;
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(remoteAddress);
        }


        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                boolean succeed = f.isSuccess();

                logger.warn("Reconnects with {}, {}.", remoteAddress, succeed ? "succeed" : "failed");

                //如果没有连接成功，继续重试操作，默认是13次
                if (!succeed) {
                    f.channel().pipeline().fireChannelInactive();
                }
            }
        });
    }

    //保证该group中的所有的channel都进行初始化操作
    private boolean isReconnectNeeded() {
        return isStarted() && (group == null || (group.size() < group.getCapacity()));
    }
}
