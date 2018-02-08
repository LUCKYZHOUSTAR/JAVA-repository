package org.lucky.transport.payload;

/**
 * @Author:chaoqiang.zhou
 * @Description:消息体bytes载体, 避免在IO线程中序列化/反序列化, jupiter-transport这一层不关注消息体的对象结构.
 * @Date:Create in 16:01 2018/2/7
 */
public abstract class BytesHolder {


    private byte serializerCode;
    private byte[] bytes;


    public byte serializerCode(){
        return serializerCode;
    }


    public byte[] bytes(){
        return bytes;
    }
    public void bytes(byte serializerCode, byte[] bytes) {
        this.serializerCode = serializerCode;
        this.bytes = bytes;
    }

    public void nullBytes() {
        bytes = null; // help gc
    }

    public int size() {
        return bytes == null ? 0 : bytes.length;
    }
}
