package basic.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:31 2017/11/28
 */
public class SyncTest {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final SyncTest test = new SyncTest();

        new Thread() {
            public void run() {
                test.get(Thread.currentThread());
            }

            ;
        }.start();

        new Thread() {
            public void run() {
                test.get(Thread.currentThread());
            }

            ;
        }.start();

    }

    public synchronized void get(Thread thread) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start <= 1) {
            System.out.println(thread.getName() + "正在进行读操作");
        }
        System.out.println(thread.getName() + "读操作完毕");
    }
}
