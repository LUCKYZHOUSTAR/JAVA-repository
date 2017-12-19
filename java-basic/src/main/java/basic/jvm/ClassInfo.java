package basic.jvm;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:58 2017/12/19
 */
public class ClassInfo {


    static {
        System.out.println("static invoker");
    }
    public ClassInfo() {
        System.out.println("构造函数初始化");

    }
}
