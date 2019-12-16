package interview.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/4 19:09
 * @Description:
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {
        String str = "hello";


        //获取一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./reade.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);


        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();


    }


    public static void test1() throws Exception {

        File file = new File("../test1.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        fileChannel.read(byteBuffer);

        fileInputStream.close();

    }


    public static void NIOFileChannel03() throws Exception {

        FileInputStream fileInputStream = new FileInputStream("1.txt");

        FileChannel fileChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");

        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);


        while (true) {

            //一定不要忘记了做清空的操作
            byteBuffer.clear();

            int read = fileChannel.read(byteBuffer);

            if (read == -1) {
                break;

            }


            byteBuffer.flip();

            fileChannel02.write(byteBuffer);
        }

        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();

    }

    public static void test2() {
        //创建一个 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
//类型化方式放入数据 buffer.putInt(100); buffer.putLong(9); buffer.putChar('尚'); buffer.putShort((short) 4);
//取出 buffer.flip();
        System.out.println();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }


    public static void readBuffer() {
        //创建一个 buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }
//读取
        buffer.flip();

        //得到一个只读的 Buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
//读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        readOnlyBuffer.put((byte) 100); //ReadOnlyBufferException
    }


    public static void mappAccessFile() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw"); //获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();
/**
 * 参数 1: FileChannel.MapMode.READ_WRITE 使用的读写模式
 * 参数 2: 0 : 可以直接修改的起始位置
 * 参数 3: 5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存 * 可以直接修改的范围就是 0-5
 * 实际类型 DirectByteBuffer
 */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        mappedByteBuffer.put(5, (byte) 'Y');//IndexOutOfBoundsException
        randomAccessFile.close(); System.out.println("修改成功~~");

    }
}
