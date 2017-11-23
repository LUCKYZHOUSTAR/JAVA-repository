package basic.constucotr;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:25 2017/11/21
 */
public class HelloA {

    public HelloA(){
        System.out.println("A1");
    }

    {
        System.out.println("A2");
    }

    static {
        System.out.println("A3");
    }
}
