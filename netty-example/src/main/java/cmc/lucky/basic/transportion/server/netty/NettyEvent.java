package cmc.lucky.basic.transportion.server.netty;

import io.netty.channel.Channel;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:14 2017/10/26
 */
public class NettyEvent {

    private final NettyEventType eventType;

    private final String remoteAddr;

    private final Channel channel;

    public NettyEvent(NettyEventType eventType, String remoteAddr, Channel channel) {
        this.eventType = eventType;
        this.remoteAddr = remoteAddr;
        this.channel = channel;
    }

    public NettyEventType getEventType() {
        return eventType;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "NettyEvent [eventType=" + eventType + ", remoteAddr=" + remoteAddr + ", channel="
                + channel + "]";
    }
}
