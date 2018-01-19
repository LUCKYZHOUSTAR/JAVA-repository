/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:54 2018/1/18
 */
public class Test {
    public static void main(String[] args) {


        short MAGIC = (short) 0xbabe;
        System.out.println(MAGIC);
        int a = 0xFFFF;//小写十六进制（等价于0x002f）
        System.out.println(a);
        int f = 0x30;//小写十六进制（等价于0x002f）
        System.out.println(f);
        System.out.println(Integer.toBinaryString(a));

        int b = 0x2F;//大写十六进制
        System.out.println(Integer.toBinaryString(b));

        int c = 10;//标准十进制
        System.out.println(Integer.toBinaryString(c));

        int d = 010;//以零开头，表示八进制
        System.out.println(Integer.toBinaryString(d));


        char e = 0xff;//char为2个字节，16位
        byte p = 0xf;//byte为8位
        short g = 0xff;//short为2个字节，16位
        System.out.println(Integer.toBinaryString(e));
        System.out.println(Integer.toBinaryString(f));
        System.out.println(Integer.toBinaryString(g));

    }
}
