package basic.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:中断就是线程的一个标识，代表线程中断过而已，但是当抛出interrunption后，该标识会自动的进行
 * 清除操作
 * @Date:Create in 20:44 2017/10/24
 */
public class Interrupted {
    public static void main(String[] args) throws Exception {
        // sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        // busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        // 休眠5秒，让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        //SleepThread interrupted is false
//        BusyThread interrupted is true

        //因为sleep中断后，抛出了InterruptedException的异常，其实在抛出这个异常之间，会先进行中断标识位的清除，因此isInterrupted返回false
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        // 防止sleepThread和busyThread立刻退出        SleepUtils.second(2);
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }
}
