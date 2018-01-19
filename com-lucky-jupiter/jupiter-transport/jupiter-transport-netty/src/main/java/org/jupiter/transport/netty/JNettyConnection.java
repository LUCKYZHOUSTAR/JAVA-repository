package org.jupiter.transport.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.jupiter.transport.JConnection;
import org.jupiter.transport.UnresolvedAddress;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 19:17 2018/1/18
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
