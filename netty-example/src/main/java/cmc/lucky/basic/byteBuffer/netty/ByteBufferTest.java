package cmc.lucky.basic.byteBuffer.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description: 内部采用了两个指针，读写的指针
 * http://frankfan915.iteye.com/blog/2199600
 * @Date:Create in 15:05 2017/12/4
 */
public class ByteBufferTest {


    @Test
    public void test1() {
        ByteBuf byteBuf = Unpooled.buffer(50);
        byteBuf.writeFloat(3.12f);
        byteBuf.writeChar(3);
        System.out.println("测类试信息".getBytes().length);
//        byteBuf.writeBytes("测类试信息".getBytes());

        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());
        System.out.println("开始读几个字符信息");
        System.out.println(byteBuf.readFloat());
        System.out.println(byteBuf.readChar());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());

        System.out.println("到目前位置，读指针已经走了一部分了，我要释放从开始到读指针之间的空间，会发生内存复制，但是会释放内存的空间");
        byteBuf.discardReadBytes();
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());

        System.out.println("现在开始调用clear方法，只是单纯的改变指针的位置");
        byteBuf.writeFloat(3.12f);
        byteBuf.writeChar(3);
//        byteBuf.clear();
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());

        byteBuf.markReaderIndex();
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());
    }


    @Test
    public void test3() {
        ByteBuf byteBuf = Unpooled.buffer(50);
        byteBuf.writeFloat(3.12f);
        byteBuf.writeChar(3);
        byteBuf.writeBytes("测类试信息".getBytes());
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());
        System.out.println("开始读几个字符信息,先标记一下");
        byteBuf.markReaderIndex();
        System.out.println("标记完后，开始读取字符");
        System.out.println(byteBuf.readFloat());
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());

        System.out.println("开始回到之前的标记信息");
        byteBuf.resetReaderIndex();
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());

        ///通过mark的索引，可以重复读取数据信息
    }


    @Test
    public void byteBufSearch() {
        ByteBuf byteBuf = Unpooled.buffer(50);
        byteBuf.writeFloat(3.12f);
        byteBuf.writeChar(3);
        byteBuf.writeBytes("测类试信息".getBytes());
        //查找首次出现的位置信息，会修改读和写的指针信息
//        byteBuf.indexOf(0,byteBuf.readableBytes(),);

        //改方法不会修改读写的指针信息
//        byteBuf.bytesBefore()

        //内置换行符的信息
//        ByteBufProcessor.FIND_CR
    }


    /**
     * 返回复制对象，共享缓冲区的内容，但是维护自己独立的读写索引
     */
    @Test
    public void dulicate(){
        ByteBuf byteBuf = Unpooled.buffer(50);
        byteBuf.writeFloat(3.12f);
//        byteBuf.writeChar(3);
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());
        ByteBuf dulicate=byteBuf.duplicate();
        dulicate.writeFloat(3.12f);
//        dulicate.writeChar(3);
        System.out.println("总容量是" + byteBuf.capacity());
        System.out.println("可写的空间还剩余" + byteBuf.writableBytes());
        System.out.println("可读的空间还剩余" + byteBuf.readableBytes());
        System.out.println("总容量是" + dulicate.capacity());
        System.out.println("可写的空间还剩余" + dulicate.writableBytes());
        System.out.println("可读的空间还剩余" + dulicate.readableBytes());
        System.out.println(byteBuf.readFloat());
        System.out.println(byteBuf.readFloat());


    }

    @Test
    public void nioBuffer(){
        ByteBuf byteBuf = Unpooled.buffer(50);
        byteBuf.writeFloat(3.12f);
        byteBuf.nioBuffer();

    }

    //模仿读取的信息
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        //4个字节存储长度信息
        if (buf.readableBytes() < 4) {
            return;
        }

        buf.markReaderIndex();
        //内容的长度信息
        int length = buf.readInt();
        if (buf.readableBytes() < length) {
            //长度信息不够
            buf.resetReaderIndex();
            return;
        }
        buf.readBytes(length);
        //do something
    }
}
