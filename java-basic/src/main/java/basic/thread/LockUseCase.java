package basic.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:chaoqiang.zhou
 * @Description: lockInterruptibly()方法比较特殊，当通过这个方法去获取锁时，如果线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态。也就使说，当两个线程同时通过lock.lockInterruptibly()想获取某个锁时，假若此时线程A获取到了锁，而线程B只有在等待，那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。
 * 其实给netty中的差不多，可以响应中断，不会想lock方法似的及时调用中断方法，也不会中断
 * <p>
 * 注意，当一个线程获取了锁之后，是不会被interrupt()方法中断的。因为本身在前面的文章中讲过单独调用interrupt()方法不能中断正在运行过程中的线程，只能中断阻塞过程中的线程。
 * <p>
 * 　　因此当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，只有进行等待的情况下，是可以响应中断的。
 * <p>
 * 　　而用synchronized修饰的话，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。
 * <p>
 * 一旦获取了锁，就不会在响应中断了，而synchronized是一直处于阻塞，无法呗中断
 * @Date:Create in 11:18 2017/10/25
 */
public class LockUseCase {
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    /**
     * Lock lock = new ReentrantLock();
     * lock.lock();
     * try {
     * } finally {
     * lock.unlock();
     * }
     */

    public static void main(String[] args) {
        //不要将获取锁的过程写在try块中，因为如果在获取锁（自定义锁的实现）时发生了异常，
//        异常抛出的同时，也会导致锁无故释放。
        lock.lock();
        try {
        } finally {
            lock.unlock();
        }
    }
}
