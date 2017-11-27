package cmc.lucky.basic.production;

import io.netty.channel.ChannelHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:48 2017/11/27
 */
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();
}
