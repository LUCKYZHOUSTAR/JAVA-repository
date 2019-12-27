package basic.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description: 等待/通知机制，是指一个线程A调用了对象O的wait()方法进入等待状态，而另一个线程B
 * 调用了对象O的notify()或者notifyAll()方法，线程A收到通知后从对象O的wait()方法返回，进而
 * 执行后续操作。上述两个线程通过对象O来完成交互，而对象上的wait()和notify/notifyAll()的
 * 关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作。
 * 锁的都是针对的是对象，因此调用该对象的notify和warit放个法，是为了唤醒等待该锁上的对象
 * <p>
 * <p>
 * ）使用wait()、notify()和notifyAll()时需要先对调用对象加锁。
 * 2）调用wait()方法后，线程状态由RUNNING变为WAITING，并将当前线程放置到对象的
 * 等待队列。
 * 3）notify()或notifyAll()方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或
 * notifAll()的线程释放锁之后，等待线程才有机会从wait()返回。4）notify()方法将等待队列中的一个等待线程从等待队列中移到同步队列中，而notifyAll()
 * 方法则是将等待队列中所有的线程全部移到同步队列，被移动的线程状态由WAITING变为
 * BLOCKED。
 * 5）从wait()方法返回的前提是获得了调用对象的锁。
 * 从上述细节中可以看到，等待/通知机制依托于同步机制，其目的就是确保等待线程从
 * wait()方法返回时能够感知到通知线程对变量做出的修改。
 * @Date:Create in 20:56 2017/10/24
 *
 *
 * 1.1个线程有两个ObjectMonitor对象列表
 * 2.ObjectMonitor对象中有两个队列:_WaitSet 和_EntryList，用来保存ObjectWaiter对象列表
 *
 * **_WaitSet ** ：处于wait状态的线程，会被加入到wait set；
 * _EntryList：处于等待锁block状态的线程，会被加入到entry set； 因为释放后，都可能还回去竞争锁
 *
 *
 *
 * ObjectWaiter
 * ObjectWaiter对象是双向链表结构，保存了_thread（当前线程）以及当前的状态TState等数据， 每个等待锁的线程都会被封装成ObjectWaiter对象。
 *
 *
 *
 * wait方法实现
 *
 *
 * lock.wait()方法最终通过ObjectMonitor的void wait(jlong millis, bool interruptable, TRAPS);实现：
 * 1、将当前线程封装成ObjectWaiter对象node；
 *
 * 2、通过ObjectMonitor::AddWaiter方法将node添加到_WaitSet列表中；
 *
 * 3、通过ObjectMonitor::exit方法释放当前的ObjectMonitor对象，这样其它竞争线程就可以获取该ObjectMonitor对象。
 * 4、最终底层的park方法会挂起线程；
 *
 *
 * notify方法实现
 * lock.notify()方法最终通过ObjectMonitor的void notify(TRAPS)实现：
 * 1、如果当前_WaitSet为空，即没有正在等待的线程，则直接返回；
 * 2、通过ObjectMonitor::DequeueWaiter方法，获取_WaitSet列表中的第一个ObjectWaiter节点，实现也很简单。
 * 这里需要注意的是，在jdk的notify方法注释是随机唤醒一个线程，其实是第一个ObjectWaiter节点
 * 3、根据不同的策略，将取出来的ObjectWaiter节点，加入到_EntryList或则通过Atomic::cmpxchg_ptr指令进行自旋操作cxq，具体代码实现有点长，这里就不贴了，有兴趣的同学可以看objectMonitor::notify方法；
 * notifyAll方法实现
 * lock.notifyAll()方法最终通过ObjectMonitor的void notifyAll(TRAPS)实现：
 * 通过for循环取出_WaitSet的ObjectWaiter节点，并根据不同策略，加入到_EntryList或则进行自旋操作。
 *
 * 从JVM的方法实现中，可以发现：notify和notifyAll并不会释放所占有的ObjectMonitor对象，其实真正释放ObjectMonitor对象的时间点是在执行monitorexit指令，一旦释放ObjectMonitor对象了，entry set中ObjectWaiter节点所保存的线程就可以开始竞争ObjectMonitor对象进行加锁操作了。
 */
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 当条件不满足时，继续wait，同时释放了lock的锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true. wa @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        //等待的时候会释放掉该锁
                        lock.wait();
                        //再次执行的时候，会从wait的地方，向后面走
                        //为何不判断flag的条件了呢
                        System.out.println("我还能输出吗" + flag);
                    } catch (InterruptedException e) {
                    }
                }
                // 条件满足时，完成工作
                System.out.println(Thread.currentThread() + " flag is false. running @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 获取lock的锁，然后进行通知，通知时不会释放lock的锁，
                // 直到当前线程释放了lock后，WaitThread才能从wait方法中返回，整个走完自己线程该走的，才会释放掉该锁
                System.out.println(Thread.currentThread() + " hold lock. notify @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }
            // 再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }
}
