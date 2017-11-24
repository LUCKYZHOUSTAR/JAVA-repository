package cmc.lucky.basic.transportion.server.netty;

import cmc.lucky.basic.transportion.server.protocol.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author:chaoqiang.zhou
 * @Description:处理业务请求
 * @Date:Create in 14:16 2017/10/26
 */
public interface NettyRequestProcessor {

    RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request)throws Exception;

}
