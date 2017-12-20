package basic.bytecode;

import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:15 2017/12/20
 */
public class ByteTest {

    public static final byte REQUEST                    = 0x01;     // Request
    public static final short MAGIC = (short) 0xbabe;

    public static void main(String[] args) {
        System.out.println(REQUEST);
        System.out.println(MAGIC);
    }
    /**
     * Java中在声明数字时默认采用的是十进制，可以在数字前加上符号表示数字采用八进制【前面加0（零）】或者十六进制【前面加上0x(零x)】。
     Java的整型封装类Integer和Long提供toString（int i,int radix）静态方法，可以将一个任意进制的整数转换为其他进制的整数。
     使用Integer或Long的toBinaryString方法将整数转换为二进制。
     使用Integer或Long的toOctalString方法将整数转换为八进制。
     使用Integer或Long的toHexString方法将整数转换为十六进制。
     使用Integer或Long的toString(int i)方法可以将其他进制的整数转换为十进制的整数的字符串表示。
     */
    /**
     * 8： 前置 0
     * 10： 不需前置
     * 16： 前置 0x 或者 0X
     */
    @Test
    public void test1() {

    }


    @Test
    public void test2() {
        int n1 = 14;
//十进制转成十六进制：
        Integer.toHexString(n1);
//十进制转成八进制
        Integer.toOctalString(n1);
//十进制转成二进制
        Integer.toBinaryString(12);

//十六进制转成十进制
        Integer.valueOf("FFFF", 16).toString();
//十六进制转成二进制
        Integer.toBinaryString(Integer.valueOf("FFFF", 16));
//十六进制转成八进制
        Integer.toOctalString(Integer.valueOf("FFFF", 16));

//八进制转成十进制
        Integer.valueOf("576", 8).toString();
//八进制转成二进制
        Integer.toBinaryString(Integer.valueOf("23", 8));
//八进制转成十六进制
        Integer.toHexString(Integer.valueOf("23", 8));

//二进制转十进制
        Integer.valueOf("0101", 2).toString();
//二进制转八进制
        Integer.toOctalString(Integer.parseInt("0101", 2));
//二进制转十六进制
        Integer.toHexString(Integer.parseInt("0101", 2));
    }


    private int i = 2010;

    @Test
    public void testInteger() {
        System.err.println();
        System.err.println("原始数据：" + i);
// 二进制转换
        System.err.println("==========整型——二进制转换==========");
        System.err.println("二进制：" + Integer.toBinaryString(i));
        System.err.println("十进制："
                + Integer.parseInt(Integer.toBinaryString(i), 2));
// 八进制转换
        System.err.println("==========整型——八进制转换==========");
        System.err.println("八进制：" + Integer.toOctalString(i));
        System.err.println("十进制："
                + Integer.parseInt(Integer.toOctalString(i), 8));
// 十六进制转换
        System.err.println("==========整型——十六进进制转换==========");
        System.err.println("十六进制：" + Integer.toHexString(i));
        System.err.println("十进制："
                + Integer.parseInt(Integer.toHexString(i), 16));
    }

    @Test
    public void testLong() {
        System.err.println();
        System.err.println("原始数据：" + i);
// 二进制转换
        System.err.println("==========长整型——二进制转换==========");
        System.err.println("二进制：" + Long.toBinaryString(i));
        System.err.println("十进制：" + Long.parseLong(Long.toBinaryString(i), 2));
// 八进制转换
        System.err.println("==========长整型——八进制转换==========");
        System.err.println("八进制：" + Long.toOctalString(i));
        System.err.println("十进制：" + Long.parseLong(Long.toOctalString(i), 8));
// 十六进制转换
        System.err.println("==========长整型——十六进进制转换==========");
        System.err.println("十六进制：" + Long.toHexString(i));
        System.err.println("十进制：" + Long.parseLong(Long.toHexString(i), 16));
    }
}
