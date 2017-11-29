package cmc.lucky.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:06 2017/11/29
 */
public class Test {


//    public int count() {
//        Thread.yield();
//    }

    public static void main(String[] args) {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(7, 7, 30, TimeUnit.SECONDS, arrayBlockingQueue, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, threadIndex.getAndIncrement() + "thread-index-");
            }
        });
        PrintTask printTask = new PrintTask();
        poolExecutor.submit(printTask);
    }

    static class PrintTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000000; i++) {
                System.out.println(Thread.currentThread().getName() + i);
                if (i % 10 == 2) {
//                    Thread.yield();
//                    Thread.currentThread().interrupt();
//                    Thread.interrupted();
                    LockSupport.park();
                }
            }
        }
    }

}
