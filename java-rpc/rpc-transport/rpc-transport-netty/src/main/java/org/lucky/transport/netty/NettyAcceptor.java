package org.lucky.transport.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.util.HashedWheelTimer;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.PlatformDependent;
import org.jupiter.common.concurrent.NamedThreadFactory;
import org.jupiter.common.util.JConstants;
import org.lucky.transport.JAcceptor;
import org.lucky.transport.JConfig;
import org.lucky.transport.JOption;
import org.lucky.transport.netty.estimator.JMessageSizeEstimator;
import org.lucky.transport.processor.ProviderProcessor;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:53 2018/2/8
 */
public abstract class NettyAcceptor implements JAcceptor {


    protected final Protocol protocol;
    protected final SocketAddress localAddress;
    protected final HashedWheelTimer timer = new HashedWheelTimer(new NamedThreadFactory("acceptor.timer"));


    private final int nBosses;
    private final int nWorkers;
    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    //堆内存，还是堆外内存
    protected volatile ByteBufAllocator allocator;

    public NettyAcceptor(Protocol protocol, SocketAddress localAddress) {
        this(protocol, localAddress, JConstants.AVAILABLE_PROCESSORS << 1);
    }

    public NettyAcceptor(Protocol protocol, SocketAddress localAddress, int nWorkers) {
        this(protocol, localAddress, 1, nWorkers);
    }

    public NettyAcceptor(Protocol protocol, SocketAddress localAddress, int nBosses, int nWorkers) {
        this.protocol = protocol;
        this.localAddress = localAddress;
        this.nBosses = nBosses;
        this.nWorkers = nWorkers;
    }


    protected void init() {
        ThreadFactory bossFactory = bossThreadFactory("jupiter.acceptor.boss");
        ThreadFactory workerFactory = workerThreadFactory("jupiter.acceptor.worker");
        boss = initEventLoopGroup(nBosses, bossFactory);
        worker = initEventLoopGroup(nWorkers, workerFactory);
        bootstrap = new ServerBootstrap().group(boss, worker);

        // parent options
        JConfig parent = configGroup().parent();
        parent.setOption(JOption.IO_RATIO, 100);

        // child options
        JConfig child = configGroup().child();
        child.setOption(JOption.IO_RATIO, 100);
        child.setOption(JOption.PREFER_DIRECT, true);
        child.setOption(JOption.USE_POOLED_ALLOCATOR, true);
    }
    @Override
    public Protocol protocol() {
        return protocol;
    }

    @Override
    public SocketAddress localAddress() {
        return localAddress;
    }

    @Override
    public int boundPort() {
        if (!(localAddress instanceof InetSocketAddress)) {
            throw new UnsupportedOperationException("Unsupported address type to get port");
        }
        return ((InetSocketAddress) localAddress).getPort();
    }

    @Override
    public void withProcessor(ProviderProcessor processor) {
        // the default implementation does nothing
    }

    @Override
    public void shutdownGracefully() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }
    protected ThreadFactory bossThreadFactory(String name) {
        return new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }

    protected ThreadFactory workerThreadFactory(String name) {
        return new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }
    protected void setOptions() {
        JConfig parent = configGroup().parent(); // parent options
        JConfig child = configGroup().child(); // child options

        setIoRatio(parent.getOption(JOption.IO_RATIO), child.getOption(JOption.IO_RATIO));

        boolean direct = child.getOption(JOption.PREFER_DIRECT);
        if (child.getOption(JOption.USE_POOLED_ALLOCATOR)) {
            if (direct) {
                allocator = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
            } else {
                allocator = new PooledByteBufAllocator(false);
            }
        } else {
            if (direct) {
                allocator = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
            } else {
                allocator = new UnpooledByteBufAllocator(false);
            }
        }
        bootstrap.childOption(ChannelOption.ALLOCATOR, allocator)
                .childOption(ChannelOption.MESSAGE_SIZE_ESTIMATOR, JMessageSizeEstimator.DEFAULT);
    }

    /**
     * Which allows easy bootstrap of {@link io.netty.channel.ServerChannel}.
     */
    protected ServerBootstrap bootstrap() {
        return bootstrap;
    }

    /**
     * The {@link EventLoopGroup} which is used to handle all the events for the to-be-creates
     * {@link io.netty.channel.Channel}.
     */
    protected EventLoopGroup boss() {
        return boss;
    }

    /**
     * The {@link EventLoopGroup} for the child. These {@link EventLoopGroup}'s are used to
     * handle all the events and IO for {@link io.netty.channel.Channel}'s.
     */
    protected EventLoopGroup worker() {
        return worker;
    }

    /**
     * Sets the percentage of the desired amount of time spent for I/O in the child event loops.
     * The default value is {@code 50}, which means the event loop will try to spend the same
     * amount of time for I/O as for non-I/O tasks.
     */
    public abstract void setIoRatio(int bossIoRatio, int workerIoRatio);

    /**
     * Create a new {@link io.netty.channel.Channel} and bind it.
     */
    protected abstract ChannelFuture bind(SocketAddress localAddress);

    protected abstract EventLoopGroup initEventLoopGroup(int nThreads, ThreadFactory tFactory);

}
