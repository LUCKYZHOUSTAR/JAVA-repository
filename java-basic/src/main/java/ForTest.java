import java.io.UnsupportedEncodingException;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/25 09:12
 * @Description:抽象类也可以继承实体类
 */
public class ForTest extends Test {

    public void dd(String[] args) {

    }

    public static void main(String[] args) {
        String str = "中国";
        try {
            //utf-8占用三个字节，gbk是两个字节
            System.out.println(str.getBytes("UTF-8").length);
            System.out.println(str.getBytes("GBK").length);
            System.out.println(str.getBytes("ISO-8859-1").length);
            System.out.println(new String(str.getBytes("ISO-8859-1"), ("ISO-8859-1")));
            System.out.println(new String(str.getBytes("UTF-8"), ("UTF-8")));
            System.out.println(new String(str.getBytes("GBK"), ("GBK")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
