package org.lucky.transport.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.lucky.transport.JConnection;
import org.lucky.transport.UnresolvedAddress;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:48 2018/2/8
 */
public abstract class JNettyConnection extends JConnection {


    private final ChannelFuture future;

    public JNettyConnection(UnresolvedAddress address, ChannelFuture future) {
        super(address);
        this.future = future;
    }

    public ChannelFuture getFuture() {
        return future;
    }

    @Override
    public void operationComplete(final Runnable callback) {
        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    callback.run();
                }
            }
        });
    }
}
