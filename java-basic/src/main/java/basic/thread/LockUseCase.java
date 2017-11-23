package basic.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:18 2017/10/25
 */
public class LockUseCase {

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
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
        } finally {
            lock.unlock();
        }
    }
}
