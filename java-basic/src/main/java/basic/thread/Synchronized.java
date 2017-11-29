package basic.thread;

/**
 * @Author:chaoqiang.zhou
 * @Description:静态方法，和该类都是对该class对象进行枷锁 ，任意线程对Object（Object由synchronized保护）的访问，首先要获得
 * Object的监视器。如果获取失败，线程进入同步队列，线程状态变为BLOCKED。当访问Object
 * 的前驱（获得了锁的线程）释放了锁，则该释放操作唤醒阻塞在同步队列中的线程，使其重新
 * 尝试对监视器的获取。
 * 当线程获取不到锁的时候，就会进入block阻塞的状态
 * @Date:Create in 17:44 2017/11/28
 */
public class Synchronized {
    public static void main(String[] args) {
        // 对Synchronized Class对象进行加锁
        synchronized (Synchronized.class) {
        }           // 静态同步方法，对Synchronized Class对象进行加锁
        m();
    }

    public static synchronized void m() {
    }
}
