package cmc.lucky.basic.production;

import io.netty.buffer.ByteBuf;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:09 2017/11/27
 */
public class ByteBufTest {
    private static final ByteBuf buf=null;

    static {

        buf.writeShort(2);//写指针+2
        buf.writeBoolean(false);//写指针+1
        buf.writeByte(1);//写指针+1
        buf.writeInt(5);//写指针+4；
        buf.writeLong(6);//写指针+8
        buf.writeChar(2);//写指针+2
        buf.writeDouble(8);//写指针+8
        buf.writeFloat(3);//写指针+4
    }
}
