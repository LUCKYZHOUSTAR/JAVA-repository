package cmc.lucky.basic.transportion.server.netty;


import cmc.lucky.basic.transportion.server.common.Pair;
import cmc.lucky.basic.transportion.server.common.RemotingUtil;
import cmc.lucky.basic.transportion.server.protocol.RemotingCommand;
import cmc.lucky.basic.transportion.server.protocol.RemotingSysResponseCode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:18 2017/10/26
 */
public class NettyRemotingServer implements RemotingServer {
    private static final Logger log = LoggerFactory.getLogger("NettyRemoting");
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup eventLoopGroupWorker;
    private EventLoopGroup eventLoopGroupBoss;
    private NettyServerConfig nettyServerConfig;
    private DefaultEventExecutorGroup defaultEventExecutorGroup;
    private int port;
    private String remoteAddr;
    protected Pair<NettyRequestProcessor, ExecutorService> requestProcessor;

    public NettyRemotingServer(int listenPort, NettyRequestProcessor processor,
                               ExecutorService executorService, String remoteAddr) {
        this.nettyServerConfig = new NettyServerConfig();
        this.port = listenPort;
        this.remoteAddr = remoteAddr;
        init(nettyServerConfig);
        this.registerProcessor(processor, executorService);
    }

    private void init(final NettyServerConfig nettyServerConfig) {
        this.serverBootstrap = new ServerBootstrap();
        this.nettyServerConfig = nettyServerConfig;
        this.eventLoopGroupBoss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(1);

            //以后线上出现问题发，方便通过线程进行定位
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyBossSelector_%d", this.index.getAndIncrement()));
            }
        });

        this.eventLoopGroupWorker = new NioEventLoopGroup(nettyServerConfig.getServerWorkerThreads(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(1);
            private int threadTotal = nettyServerConfig.getServerSelectorThreads();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyServerSelector_%d_%d",
                        threadTotal, this.threadIndex.incrementAndGet()));
            }
        });
    }

    @Override
    public void registerProcessor(NettyRequestProcessor processor, ExecutorService executorService) {
        this.requestProcessor = new Pair<NettyRequestProcessor, ExecutorService>(processor,
                executorService);
    }

    @Override
    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(
                nettyServerConfig.getServerWorkerThreads(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "NettyServerWorkerThread_"
                        + this.threadIndex.incrementAndGet());
            }
        });
        ServerBootstrap childHandler = this.serverBootstrap
                .group(this.eventLoopGroupBoss, this.eventLoopGroupWorker)
                .channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true).option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())
                .option(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())
                .localAddress(this.remoteAddr, this.nettyServerConfig.getListenPort())
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                defaultEventExecutorGroup,
                                new NettyEncoder(),
                                new NettyDecoder(),
                                new IdleStateHandler(0, 0, nettyServerConfig
                                        .getServerChannelMaxIdleTimeSeconds()), new NettyConnetManageHandler(),
                                new NettyServerHandler());

                    }

                });
        if (nettyServerConfig.isServerPooledByteBufAllocatorEnable()) {
            // 这个选项有可能会占用大量堆外内存，暂时不使用。
            childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        }

        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();
            InetSocketAddress address = (InetSocketAddress) sync.channel().localAddress();
            this.port = address.getPort();

        } catch (Exception e) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e);
        }

    }

    @Override
    public void shutDown() {
        try {
            this.eventLoopGroupBoss.shutdownGracefully();
            this.eventLoopGroupWorker.shutdownGracefully();
            if (this.defaultEventExecutorGroup != null) {
                this.defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            log.error("NettyRemotingServer shutdown exception, ", e);
        }
    }

    @Override
    public int localListenPort() {
        return this.port;
    }


    class NettyServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg)
                throws Exception {
            if (msg != null) {
                processRequestCommand(ctx, msg);
            }
        }

    }


    private void processRequestCommand(final ChannelHandlerContext ctx,
                                       final RemotingCommand remotingCommand) {
        //业务处理的对象和线程的集合
        final Pair<NettyRequestProcessor, ExecutorService> pair = this.requestProcessor;
        //具体的业务处理也封装成了一个runnable，用来线程上的处理操作
        if (pair != null) {
            Runnable run = new Runnable() {

                @Override
                public void run() {
                    try {
                        //执行具体的业务处理的流程操作
                        final RemotingCommand responseCommand = pair.getObject1().processRequest(
                                ctx, remotingCommand);
                        if (responseCommand != null) {
                            responseCommand.setType(RemotingCommand.RemotingCommandType.RESPONSE_COMMAND);
                            try {
                                //给客户端返回操作
                                ctx.writeAndFlush(responseCommand);
                            } catch (Exception e) {
                                log.error("process request over, but response failed", e);
                                log.error(remotingCommand.toString());
                                log.error(responseCommand.toString());
                            }
                        } else {
                            //收到了请求，但是没有返回应答，可能是processRequest已经进行了应答
                        }
                    } catch (Exception e) {
                        log.error("process request exception", e);
                        log.error(remotingCommand.toString());
                        final RemotingCommand response = RemotingCommand
                                .createResponseCommand(
                                        RemotingSysResponseCode.SYSTEM_ERROR, //
                                        RemotingUtil.exceptionSimpleDesc(e));
                        ctx.writeAndFlush(response);
                    }

                }
            };

            //用线程来处理业务
            try {
                //这里需要做流控，要求线程池对应的队列必须是有大小限制的
                pair.getObject2().submit(run);
            } catch (RejectedExecutionException e) {
                // 每个线程10s打印一次
                if ((System.currentTimeMillis() % 10000) == 0) {
                    log.warn(RemotingUtil.parseChannelRemoteAddr(ctx.channel()) //
                            + ", too many requests and system thread pool busy, RejectedExecutionException " //
                            + pair.getObject2().toString());
                }
                final RemotingCommand response = RemotingCommand.createResponseCommand(
                        RemotingSysResponseCode.SYSTEM_BUSY,
                        "too many requests and system thread pool busy, please try another server");
                ctx.writeAndFlush(response);
            }
        } else {
            String error = " request type not supported";
            final RemotingCommand response = RemotingCommand
                    .createResponseCommand(
                            RemotingSysResponseCode.REQUEST_CODE_NOT_SUPPORTED,
                            error);
            ctx.writeAndFlush(response);
            log.error(RemotingUtil.parseChannelRemoteAddr(ctx.channel()) + error);
        }
    }


    //同事继承了入站和出战，用来拦截连接和用户的事件操作,做打印日志的操作
    class NettyConnetManageHandler extends ChannelDuplexHandler {

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelRegistered {}", remoteAddress);
            super.channelRegistered(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelUnregistered, the channel[{}]", remoteAddress);
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelActive, the channel[{}]", remoteAddress);
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelInactive, the channel[{}]", remoteAddress);
            super.channelInactive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // TODO Auto-generated method stub
            super.channelRead(ctx, msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            // TODO Auto-generated method stub
            super.channelReadComplete(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state().equals(IdleState.ALL_IDLE)) {
                    //如果该channel空闲的话，就关闭该通道
                    final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
                    log.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                    RemotingUtil.closeChannel(ctx.channel());
                }
            }
            ctx.fireUserEventTriggered(evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.warn("NETTY SERVER PIPELINE: exceptionCaught {}", remoteAddress);
            log.warn("NETTY SERVER PIPELINE: exceptionCaught exception.", cause);
            RemotingUtil.closeChannel(ctx.channel());
        }

    }
}
