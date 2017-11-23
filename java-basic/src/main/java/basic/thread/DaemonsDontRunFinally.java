package basic.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * 将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。在使用守护线程时需要注意一下几点：

(1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。

(2) 在Daemon线程中产生的新线程也是Daemon的。

(3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
 * @Date:Create in 18:31 2017/11/23
 */
public class DaemonsDontRunFinally {
    /**

     * @param args

     */

    public static void main(String[] args) {

        Thread t = new Thread(new ADaemon());

//        t.setDaemon(true);//加上这句话代表是一个守护线程，如果main方法的用户线程推出后，该线程也自动退出
        //如果去掉后，代表就是用户线程，给mian方法有同一个级别，都会执行

        t.start();

    }



}



class ADaemon implements Runnable {



    @Override
    public void run() {

        try {

            System.out.println("start ADaemon...");

            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {

            System.out.println("Exiting via InterruptedException");

        } finally {

            System.out.println("This shoud be always run ?");

        }

    }


}
