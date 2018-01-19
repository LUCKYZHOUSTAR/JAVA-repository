package org.jupiter.transport;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:00 2018/1/18
 */
public class JProtocolHeader {

    public static final int HEAD_LENGTH = 16;
    public static final short MAGIC = (short) 0xbabe;

    public static final byte REQUEST = 0x01;     // Request
    public static final byte RESPONSE = 0x02;     // Response
    public static final byte PUBLISH_SERVICE = 0x03;     // 发布服务
    public static final byte PUBLISH_CANCEL_SERVICE = 0x04;     // 取消发布服务
    public static final byte SUBSCRIBE_SERVICE = 0x05;     // 订阅服务
    public static final byte OFFLINE_NOTICE = 0x06;     // 通知下线
    public static final byte ACK = 0x07;     // Acknowledge
    public static final byte HEARTBEAT = 0x0f;     // Heartbeat

    private byte messageCode;       // sign 低地址4位
    /**
     * Serializer Code: 0x01 ~ 0x0f ================================================================================
     */
    // 位数限制最多支持15种不同的序列化/反序列化方式
    // protostuff   = 0x01
    // hessian      = 0x02
    // kryo         = 0x03
    // java         = 0x04
    // ...
    // XX1          = 0x0e
    // XX2          = 0x0f
    private byte serializerCode;    // sign 高地址4位
    private byte status;            // 响应状态码
    private long id;                // request.invokeId, 用于映射 <id, request, response> 三元组
    private int bodyLength;         // 消息体长度


    public static byte toSign(byte serializerCode, byte messageCode) {
        return (byte) ((serializerCode << 4) + messageCode);
    }

    public void sign(byte sign) {
        // sign 低地址4位
        this.messageCode = (byte) (sign & 0x0f);
        // sign 高地址4位, 先转成无符号int再右移4位
        this.serializerCode = (byte) ((((int) sign) & 0xff) >> 4);
    }

    public byte messageCode() {
        return messageCode;
    }

    public byte serializerCode() {
        return serializerCode;
    }

    public byte status() {
        return status;
    }

    public void status(byte status) {
        this.status = status;
    }

    public long id() {
        return id;
    }

    public void id(long id) {
        this.id = id;
    }

    public int bodyLength() {
        return bodyLength;
    }

    public void bodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    @Override
    public String toString() {
        return "JProtocolHeader{" +
                "messageCode=" + messageCode +
                ", serializerCode=" + serializerCode +
                ", status=" + status +
                ", id=" + id +
                ", bodyLength=" + bodyLength +
                '}';
    }
}
