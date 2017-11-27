package cmc.lucky.basic.production.common;

import io.netty.channel.Channel;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:50 2017/11/27
 */
public class NettyEvent {
    private final NettyEventType type;
    private final String remoteAddr;
    private final Channel channel;

    public NettyEvent(NettyEventType type, String remoteAddr, Channel channel) {
        this.type = type;
        this.remoteAddr = remoteAddr;
        this.channel = channel;
    }


    public NettyEventType getType() {
        return type;
    }


    public String getRemoteAddr() {
        return remoteAddr;
    }


    public Channel getChannel() {
        return channel;
    }


    @Override
    public String toString() {
        return "NettyEvent [type=" + type + ", remoteAddr=" + remoteAddr + ", channel=" + channel + "]";
    }
}
