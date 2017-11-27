package cmc.lucky.basic.attributeMapConstant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:31 2017/11/27
 */
public class Handler2 extends ChannelOutboundHandlerAdapter {


    public Handler2() {
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("我是handler2，我是out，该我了饿吗");
        super.write(ctx, msg, promise);
    }
}
