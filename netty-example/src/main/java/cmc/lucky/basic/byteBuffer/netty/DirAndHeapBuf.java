package cmc.lucky.basic.byteBuffer.netty;

import io.netty.buffer.*;
import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.internal.PlatformDependent;
import org.junit.Test;

import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @Author:chaoqiang.zhou
 * @Description:直接或者间接buf，一个是jvm管理回收，也是直接操作内存的信息
 * @Date:Create in 16:04 2017/12/4
 */
public class DirAndHeapBuf {


    /**
     * headbyteBuf：内存的分配和回收速度快，可以被JVM自动回收，缺点就是如果进行sockett的IO读写，需要额外做一次内存复制，将对外内存对应的缓冲区赋值到内核channel中，性能有一定程度的下降
     * DirectByteBuf：非堆内存，在堆外进行内存分配，相比堆内存，分配和回收速度会慢一些，但是写入或者从socketchannel中读取时，少了一次的内存复制，速度比堆内存快
     */


    /**
     * 经验表明，ByteBuf的最佳实践是在IO线程的读写缓冲区使用DirectByteBuf，后端业务消息的编解码使用headbyteBuf
     */


    /**
     * https://yq.aliyun.com/articles/55623
     */
    @Test
    public void test1() {
        //headbyteBuf
//        ByteBuf byteBuf= Unpooled.buffer(50);
        PooledByteBufAllocator alloc = new PooledByteBufAllocator();

        new FastThreadLocalThread(new Runnable() {

            @Override

            public void run() {

                for (int i = 0; i < 3; i++) {

                    String name = Thread.currentThread().getName();

                    long start = System.currentTimeMillis();

                    ByteBuf buf = alloc.buffer(102400);

                    long end = System.currentTimeMillis();

                    System.out.println(name + "\t" + buf + ", time:" + (end - start));

                }

            }

        }).start();
    }


    @Test
    public void test2() {
        UnpooledByteBufAllocator alloc = UnpooledByteBufAllocator.DEFAULT;
        ByteBuf bytebuf = alloc.buffer();
        System.out.println(alloc.isDirectBufferPooled());//false
    }


    @Test
    public void test3() {
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf byteBuf = allocator.buffer();
        System.out.println(allocator.isDirectBufferPooled());
    }

    @Test
    public void test4() {
        ByteBufAllocator allocator = null;
//        boolean direct = child.getOption(JOption.PREFER_DIRECT);
        boolean direct = false;
        boolean isPooled = false;
        if (isPooled) {
            if (direct) {
                allocator = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
            } else {
                allocator = new PooledByteBufAllocator(false);
            }
        } else {
            if (direct) {
                allocator = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
            } else {
                allocator = new UnpooledByteBufAllocator(false);
            }
        }

        ByteBuf byteBuf = allocator.buffer();
    }


    @Test
    public void byteUtil(){
        ByteBufAllocator allocator = null;
//        boolean direct = child.getOption(JOption.PREFER_DIRECT);
        boolean direct = false;
        boolean isPooled = false;
        if (isPooled) {
            if (direct) {
                allocator = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
            } else {
                allocator = new PooledByteBufAllocator(false);
            }
        } else {
            if (direct) {
                allocator = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
            } else {
                allocator = new UnpooledByteBufAllocator(false);
            }
        }
        CharBuffer charBuffer=null;
        ByteBufUtil.encodeString(allocator,charBuffer, Charset.defaultCharset());
    }
}
