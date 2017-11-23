package basic.constucotr;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:24 2017/11/21
 */
public class HelloB extends HelloA{


    public HelloB(){
        System.out.println("B1");
    }

    {
        System.out.println("B2");
    }

    static {
        System.out.println("B3");
    }

    public static void main(String[] args) {
        System.out.println("start");
        new HelloB();
        new  HelloB();
        System.out.println("end");
    }
}
