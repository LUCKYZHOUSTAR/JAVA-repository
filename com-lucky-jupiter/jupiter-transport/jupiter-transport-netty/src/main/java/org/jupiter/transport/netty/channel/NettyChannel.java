package org.jupiter.transport.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.jupiter.transport.channel.JChannel;
import org.jupiter.transport.channel.JFutureListener;

import java.net.SocketAddress;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 13:44 2018/1/23
 */
public class NettyChannel implements JChannel {

    private static final AttributeKey<NettyChannel> NETTY_CHANNEL_KEY = AttributeKey.valueOf("netty.channel");


    public static NettyChannel attachNettyChannel(Channel channel) {
        Attribute<NettyChannel> attr = channel.attr(NETTY_CHANNEL_KEY);
        NettyChannel nChannel = attr.get();

        if (nChannel == null) {
            NettyChannel newNChannel = new NettyChannel(channel);
            nChannel = attr.setIfAbsent(newNChannel);
            if (nChannel == null) {
                nChannel = newNChannel;
            }
        }

        return nChannel;
    }

    private final Channel channel;

    private NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String id() {
        return channel.id().asShortText();
    }

    @Override
    public boolean isActive() {
        return channel.isActive();
    }

    @Override
    public boolean inIoThread() {
        return channel.eventLoop().inEventLoop();
    }

    @Override
    public SocketAddress localAddress() {
        return channel.localAddress();
    }

    @Override
    public SocketAddress remoteAddress() {
        return channel.remoteAddress();
    }

    @Override
    public boolean isWritable() {
        return channel.isWritable();
    }

    @Override
    public boolean isMarkedReconnect() {
//        ConnectionWatchdog watchdog = channel.pipeline().get(ConnectionWatchdog.class);
//        return watchdog != null && watchdog.isStarted();
        return false;
    }

    @Override
    public boolean isAutoRead() {
        return channel.config().isAutoRead();
    }

    @Override
    public void setAutoRead(boolean autoRead) {
        channel.config().setAutoRead(autoRead);
    }

    @Override
    public JChannel close() {
        channel.close();
        final JChannel jChannel = this;
        channel.close().addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
//                if (future.isSuccess()) {
//                    listener.operationSuccess(jChannel);
//                } else {
//                    listener.operationFailure(jChannel, future.cause());
//                }
            }
        });
        return jChannel;
    }

    @Override
    public JChannel close(JFutureListener<JChannel> listener) {
        return null;
    }

    @Override
    public JChannel write(Object msg) {
        return null;
    }

    @Override
    public JChannel write(Object msg, JFutureListener<JChannel> listener) {
        return null;
    }
}
