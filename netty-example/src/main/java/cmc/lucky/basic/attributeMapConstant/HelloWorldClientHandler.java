package cmc.lucky.basic.attributeMapConstant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;

import java.util.Date;

import static cmc.lucky.basic.attributeMapConstant.AttributeMapConstant.NETTY_CHANNEL_KEY;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:24 2017/11/27
 */
public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Attribute<NettyChannel> attr = ctx.attr(NETTY_CHANNEL_KEY);

        NettyChannel nChannel = attr.get();
        if (nChannel == null) {
            NettyChannel nettyChannel = new NettyChannel("HelloWorldClient", new Date());
            nChannel = attr.setIfAbsent(nettyChannel);
        } else {
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "=======" + nChannel.getCreateDate());
        }
        System.out.println("HelloWorldC0ientHandler Active");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg instanceof String);
        Attribute<NettyChannel> attr = ctx.channel().attr(NETTY_CHANNEL_KEY);
        NettyChannel nChannel = attr.get();
        if (nChannel == null) {
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            nChannel = attr.setIfAbsent(newNChannel);
        } else {
            System.out.println("channelRead attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "=======" + nChannel.getCreateDate());
        }
        System.out.println("HelloWorldClientHandler read Message:" + msg);

        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
