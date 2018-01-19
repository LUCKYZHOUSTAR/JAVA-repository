package org.jupiter.transport.channel;

import java.net.SocketAddress;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:19 2018/1/18
 */
public interface JChannel {


    /**
     * A {@link JFutureListener} that closes the {@link JChannel}.
     */
    JFutureListener<JChannel> CLOSE = new JFutureListener<JChannel>() {

        @Override
        public void operationSuccess(JChannel channel) throws Exception {
            channel.close();
        }

        @Override
        public void operationFailure(JChannel channel, Throwable cause) throws Exception {
            channel.close();
        }
    };

    /**
     * Returns the identifier of this {@link JChannel}.
     */
    String id();

    /**
     * Return {@code true} if the {@link JChannel} is active and so connected.
     */
    boolean isActive();

    /**
     * Return {@code true} if the current {@link Thread} is executed in the
     * IO thread, {@code false} otherwise.
     */
    boolean inIoThread();

    /**
     * Returns the local address where this channel is bound to.
     */
    SocketAddress localAddress();

    /**
     * Returns the remote address where this channel is connected to.
     */
    SocketAddress remoteAddress();

    /**
     * Returns {@code true} if and only if the I/O thread will perform the
     * requested write operation immediately.
     * Any write requests made when this method returns {@code false} are
     * queued until the I/O thread is ready to process the queued write requests.
     */
    boolean isWritable();

    /**
     * Is set up automatic reconnection.
     */
    boolean isMarkedReconnect();

    /**
     * Returns {@code true} if and only if read(socket) will be invoked
     * automatically so that a user application doesn't need to call it
     * at all. The default value is {@code true}.
     */
    boolean isAutoRead();

    /**
     * Sets if read(socket) will be invoked automatically so that a user
     * application doesn't need to call it at all. The default value is
     * {@code true}.
     */
    void setAutoRead(boolean autoRead);

    /**
     * Requests to close this {@link JChannel}.
     */
    JChannel close();

    /**
     * Requests to close this {@link JChannel}.
     */
    JChannel close(JFutureListener<JChannel> listener);

    /**
     * Requests to write a message on the channel.
     */
    JChannel write(Object msg);

    /**
     * Requests to write a message on the channel.
     */
    JChannel write(Object msg, JFutureListener<JChannel> listener);
}
