package basic.thread;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 21:26
 * @Description:https://www.jianshu.com/p/f4454164c017
 * java的Object.wait和notify为什么要求当前线程在调用这两个方法时必须先获得该对象的锁?
 */
public class Test1 {

    private static String OBJECT = "OBJECT";

    public static void main(String[] args) {

        synchronized (OBJECT) {
            try {
                OBJECT.wait();
            } catch (InterruptedException e) {
            }
        }


        System.out.println("");


    }
}
