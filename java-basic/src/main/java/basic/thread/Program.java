package basic.thread;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description:假设有二个线程，一个是常规的用户线程，不停写入日志，另一个是守护线程，在空闲时清理日志（仅保留最近的5条日志）
 * @Date:Create in 18:35 2017/11/23
 */
public class Program {
    private static int queueCapacity = 10;
    private static BlockingQueue<String> logQueue = new ArrayBlockingQueue<String>(queueCapacity);

    public static void main(String[] args) throws IOException {

        LogWriter writer = new LogWriter();
        LogCleaner cleaner = new LogCleaner();
        cleaner.setDaemon(true);

        writer.start();
        cleaner.start();
    }

    /**
     * 模拟不停写日志（直到队列写满）
     */
    private static class LogWriter extends Thread {
        private AtomicLong index = new AtomicLong();
//        @Override
//        public void run() {
//            for (int i = 0; i < queueCapacity; i++) {
//                try {
//                    logQueue.put("" + i);
//                    System.out.println("日志已写入，当前日志内容：" + logQueue);
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


        @Override
        public void run() {
            while (true) {
                try {
                    logQueue.put("" + index.getAndIncrement());
                    System.out.println("日志已写入，当前日志内容：" + logQueue);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 模拟在空闲时清理日志（仅保留5条日志）
     */
    private static class LogCleaner extends Thread {
        @Override
        public void run() {
            while (true) {
                if (logQueue.size() > 5) {
                    try {
                        logQueue.take();
                        System.out.println("多余日志被清理，当前日志内容：" + logQueue);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
