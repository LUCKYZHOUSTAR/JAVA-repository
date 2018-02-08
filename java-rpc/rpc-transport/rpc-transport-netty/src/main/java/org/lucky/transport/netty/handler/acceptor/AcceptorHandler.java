package org.lucky.transport.netty.handler.acceptor;

import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import org.jupiter.common.util.Signal;
import org.jupiter.common.util.internal.logging.InternalLogger;
import org.jupiter.common.util.internal.logging.InternalLoggerFactory;
import org.lucky.transport.Status;
import org.lucky.transport.channel.LChannel;
import org.lucky.transport.netty.channel.NettyChannel;
import org.lucky.transport.payload.LRequestBytes;
import org.lucky.transport.processor.ProviderProcessor;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.jupiter.common.util.StackTraceUtil.stackTrace;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:45 2018/2/7
 */
@ChannelHandler.Sharable
public class AcceptorHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AcceptorHandler.class);

    private static final AtomicInteger channelCounter = new AtomicInteger(0);

    private ProviderProcessor processor;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel ch = ctx.channel();
        if (msg instanceof LRequestBytes) {
            //缓存了channel，每个服务的多个channel，只有一个即可
            LChannel lChannel = NettyChannel.attachChannel(ch);
            try {
                //处理的逻辑不在这里在rpc的层面进行处理对应的逻辑信息
                processor.handleRequest(lChannel, (LRequestBytes) msg);
            } catch (Throwable t) {
                processor.handleException(lChannel, (LRequestBytes) msg, Status.SERVER_ERROR, t);
            }
        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("Unexcepted message type received:{},channel:{}", msg.getClass(), ch);
            }

            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int count = channelCounter.incrementAndGet();

        logger.info("Connects with {} as the {}th channel.", ctx.channel(), count);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        int count = channelCounter.getAndDecrement();

        logger.warn("Disconnects with {} as the {}th channel.", ctx.channel(), count);

        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        ChannelConfig config = ch.config();

        // 高水位线: ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK
        // 低水位线: ChannelOption.WRITE_BUFFER_LOW_WATER_MARK
        if (!ch.isWritable()) {
            // 当前channel的缓冲区(OutboundBuffer)大小超过了WRITE_BUFFER_HIGH_WATER_MARK
            if (logger.isWarnEnabled()) {
                logger.warn("{} is not writable, high water mask: {}, the number of flushed entries that are not written yet: {}.",
                        ch, config.getWriteBufferHighWaterMark(), ch.unsafe().outboundBuffer().size());
            }

            config.setAutoRead(false);
        } else {
            // 曾经高于高水位线的OutboundBuffer现在已经低于WRITE_BUFFER_LOW_WATER_MARK了
            if (logger.isWarnEnabled()) {
                logger.warn("{} is writable(rehabilitate), low water mask: {}, the number of flushed entries that are not written yet: {}.",
                        ch, config.getWriteBufferLowWaterMark(), ch.unsafe().outboundBuffer().size());
            }

            config.setAutoRead(true);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel ch = ctx.channel();

        if (cause instanceof Signal) {
            logger.error("An I/O signal was caught: {}, force to close channel: {}.", ((Signal) cause).name(), ch);

            ch.close();
        } else if (cause instanceof IOException) {
            logger.error("An I/O exception was caught: {}, force to close channel: {}.", stackTrace(cause), ch);

            ch.close();
        } else {
            logger.error("An unexpected exception was caught: {}, channel: {}.", stackTrace(cause), ch);
        }
    }

    public ProviderProcessor processor() {
        return processor;
    }

    public void processor(ProviderProcessor processor) {
        this.processor = processor;
    }
}
