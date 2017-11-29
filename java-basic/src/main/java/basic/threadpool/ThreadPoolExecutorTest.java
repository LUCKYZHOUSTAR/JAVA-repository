package basic.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:51 2017/11/29
 */
public class ThreadPoolExecutorTest {


    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

      
        for (int i = 0; i < 100; i++) {
            task task = new task();
            executor.submit(task);
        }


        for (int i = 0; i < 100; i++) {
            resultTask resultTask = new resultTask(i);
            Future<String> result = executor.submit(resultTask);
            System.out.println(result.get());
        }
    }


    static class task implements Runnable {


        AtomicLong index = new AtomicLong();

        @Override
        public void run() {
            System.out.println("我开始执行了" + index.getAndIncrement());
        }
    }


    static class resultTask implements Callable {


        public resultTask(int index) {
            this.index = index;
        }

        private int index;

        @Override
        public String call() throws Exception {

            return "ahha" + index;
        }
    }
}
