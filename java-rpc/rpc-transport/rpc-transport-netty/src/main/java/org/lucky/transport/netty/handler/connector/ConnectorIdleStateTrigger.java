package org.lucky.transport.netty.handler.connector;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.lucky.transport.netty.Heartbeats;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:24 2018/2/8
 */
@ChannelHandler.Sharable
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            //特定的时间内，没有进行写操作的话，就发送一个字节，告诉服务端我还活着
            if (state == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(Heartbeats.heartbeatContent());
            }
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }
}
