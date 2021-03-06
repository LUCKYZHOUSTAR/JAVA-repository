package basic.lock;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/8/8 13:51
 * @Description:
 */
public class AbstractQueuedSynchronizerTest {
    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;


    private static final Lock lock = new ReentrantLock();
    public static void main(String[] args) throws Exception{


        System.out.println(1 & EXCLUSIVE_MASK);
        System.out.println(12 >>> SHARED_SHIFT);

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        executorService.submit(AbstractQueuedSynchronizerTest::action);
        executorService.submit(AbstractQueuedSynchronizerTest::action);

        executorService.awaitTermination(200, TimeUnit.SECONDS);
        executorService.shutdown();

    }


    private static void action() {
        System.out.printf("当前线程[%s],正在等待您的输入",Thread.currentThread().getName());

            lock.lock();
        try {
            System.in.read();
            System.out.printf("当前线程[%s],执行完毕",Thread.currentThread().getName());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
