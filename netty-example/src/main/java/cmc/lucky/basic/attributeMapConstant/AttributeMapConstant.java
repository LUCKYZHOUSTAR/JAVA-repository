package cmc.lucky.basic.attributeMapConstant;

import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @Author:chaoqiang.zhou
 * @Description:AttributeMap这是是绑定在Channel或者ChannelHandlerContext上的一个附件，相当于依附在这两个对象上的寄生虫一样，相当于附件一样，如图所示：
 * 如果是绑在channelHandlerContext上的话，那么对于每个上下文是独立的
 * 如果绑定在channel上的话，对于所有的上下文是共享的
 * @Date:Create in 10:59 2017/11/27
 */
@ToString
@AllArgsConstructor
public class AttributeMapConstant {
    public static final AttributeKey<NettyChannel> NETTY_CHANNEL_KEY = AttributeKey.valueOf("netty.channel");
}
