package basic.concurrent;

/**
 * @Author:chaoqiang.zhou
 * @Description:join用于让当前执行线程等待join线程执行结束。其实现原理是不停检查join线程是否存 活，如果join线程存活则让当前线程永远等待。其中，wait（0）表示永远等待下去，代码片段如
 * 下。
 * 其实join方法就是不断的调用线程的是否活用的方法，如果活用则等待
 * @Date:Create in 19:15 2017/11/28
 */
public class JoinCountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
            }
        });
        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2 finish");
            }
        });
        parser1.start();
        parser2.start();
        parser1.join();
        parser2.join();
        System.out.println("all parser finish");
    }
}

