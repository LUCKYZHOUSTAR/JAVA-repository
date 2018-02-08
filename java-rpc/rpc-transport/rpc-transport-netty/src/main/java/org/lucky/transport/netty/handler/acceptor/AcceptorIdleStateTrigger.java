package org.lucky.transport.netty.handler.acceptor;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.lucky.transport.exception.IoSignals;

/**
 * @Author:chaoqiang.zhou
 * @Description:多个channel之间共享，用来处理心跳
 * @Date:Create in 10:14 2018/2/8
 */
@ChannelHandler.Sharable
public class AcceptorIdleStateTrigger extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            //如果在特定时间内，没有读取到客户端的心跳包，证明改客户端已经over,异常就会抛出到acceptorhandler中，进行后续的channel关闭操作
            if (state == IdleState.READER_IDLE) {
                throw IoSignals.READER_IDLE;
            }
        } else {
            super.userEventTriggered(ctx, evt);

        }
    }
}
