package basic.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 19:08 2017/11/16
 */
public class WaitTimeOutTest {

    private Object result;

    private ReentrantLock lock = new ReentrantLock();
    private Condition hasMessage = lock.newCondition();

    public void reset() {
        this.result = null;
    }


    public Object get(long timeOut) throws InterruptedException {
        lock.lock();
        try {
            long end = System.currentTimeMillis() + timeOut;
            long remain = timeOut;
            while ((result == null)) {
                boolean ok = hasMessage.await(remain, TimeUnit.MILLISECONDS);
                if (ok || (remain = end - System.currentTimeMillis()) <= 0) {
                    break;
                }
            }
        } finally {
            lock.unlock();
        }

        return result;
    }

    public void set(Object obj) {
        lock.lock();
        try {
            result = obj;
            hasMessage.signal();
        } finally {
            lock.unlock();
        }
    }
}
