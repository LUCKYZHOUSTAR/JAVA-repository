package basic.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:https://www.cnblogs.com/yjmyzz/p/daemon-thread-demo.html
 * 就是一个守护线程，等用户线程推出后，就自动的退出
 * @Date:Create in 18:27 2017/11/23
 */
public class Daemons {
    /**

     * @param args

     * @throws InterruptedException

     */

    public static void main(String[] args) throws InterruptedException {

        Thread d = new Thread(new Daemon());

//        d.setDaemon(true); //必须在启动线程前调用

        d.start();

        System.out.println("d.isDaemon() = " + d.isDaemon() + ".");

//        TimeUnit.SECONDS.sleep(1);如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。下面的例子也说明了这个问题。

    }



}



class DaemonSpawn implements Runnable {


    @Override
    public void run() {

        while (true) {

            Thread.yield();

        }

    }



}



class Daemon implements Runnable {

    private Thread[] t = new Thread[10];



    @Override
    public void run() {

        for (int i=0; i<t.length; i++) {

            t[i] = new Thread(new DaemonSpawn());

            t[i].start();

            System.out.println("DaemonSpawn " + i + " started.");

        }

        for (int i=0; i<t.length; i++) {

            System.out.println("t[" + i + "].isDaemon() = " +

                    t[i].isDaemon() + ".");

        }

        while (true) {

            Thread.yield();

        }

    }
}
