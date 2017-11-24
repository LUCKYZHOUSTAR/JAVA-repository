package cmc.lucky.basic.transportion.server.netty;

import cmc.lucky.basic.transportion.server.common.RemotingUtil;
import cmc.lucky.basic.transportion.server.protocol.RemotingCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:32 2017/10/26
 */
public class NettyEncoder extends MessageToByteEncoder<RemotingCommand> {
    private static final Logger log = LoggerFactory.getLogger(RemotingUtil.RemotingLogName);

    @Override
    public void encode(ChannelHandlerContext ctx, RemotingCommand cmd, ByteBuf out)
            throws Exception {
        try {
            int length = 0;
            if (cmd.getBody() != null) {
                length += cmd.getBody().length;
            }

            ByteBuffer result = ByteBuffer.allocate(8 + length);
            String preLen = String.format("%08d", length);
            // length
            result.put(preLen.getBytes());
            // body data;
            if (cmd.getBody() != null) {
                result.put(cmd.getBody());
            }

            result.flip();

            out.writeBytes(result);
        } catch (Exception e) {
            log.error("encode exception, " + RemotingUtil.parseChannelRemoteAddr(ctx.channel()), e);
            if (cmd != null) {
                log.error(cmd.toString());
            }
            RemotingUtil.closeChannel(ctx.channel());
        }
    }
}
