package cmc.lucky.basic.transportion.server.netty;


import cmc.lucky.basic.transportion.server.common.RemotingUtil;
import cmc.lucky.basic.transportion.server.protocol.RemotingCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:31 2017/10/26
 */
public class NettyDecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger log = LoggerFactory.getLogger(RemotingUtil.RemotingLogName);
    private static final int FRAME_MAX_LENGTH = //
            Integer.parseInt(System.getProperty("com.remoting.frameMaxLength", "8388608"));


    public NettyDecoder() {
        super(FRAME_MAX_LENGTH, 0, 8, 0, 8);
    }


    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = null;
        try {
            frame = (ByteBuf) super.decode(ctx, in);
            if (null == frame) {
                return null;
            }

            ByteBuffer byteBuffer = frame.nioBuffer();
            int len = byteBuffer.capacity();
            byte[] bodyData = new byte[len];
            byteBuffer.get(bodyData);
            RemotingCommand cmd = RemotingCommand.createRequestCommand();
            cmd.setBody(bodyData);
            return cmd;
        } catch (Exception e) {
            log.error("decode exception, " + RemotingUtil.parseChannelRemoteAddr(ctx.channel()), e);
            RemotingUtil.closeChannel(ctx.channel());
        } finally {
            if (null != frame) {
                frame.release();
            }
        }

        return null;
    }
    @Override
    public long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        byte[] lendata = new byte[8];
        buf.getBytes(0, lendata);
        int frameLength = Integer.parseInt(new String(lendata));
        return frameLength;
    }
}
