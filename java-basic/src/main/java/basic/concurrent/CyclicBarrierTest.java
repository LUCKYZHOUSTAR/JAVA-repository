package basic.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 19:19 2017/11/28
 */
public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                    System.out.println("我执行了");
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();
        try {
            c.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }
}
