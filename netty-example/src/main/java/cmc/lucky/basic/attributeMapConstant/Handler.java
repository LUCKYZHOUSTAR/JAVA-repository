package cmc.lucky.basic.attributeMapConstant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:29 2017/11/27
 */
public class Handler extends StringEncoder {


    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        System.out.println("我是handler就想知道出去的顺序，是谁在前，谁在后");
        super.encode(ctx, msg, out);
    }
}
