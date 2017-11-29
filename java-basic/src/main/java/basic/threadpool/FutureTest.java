package basic.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 19:41 2017/11/28
 */
public class FutureTest {

    public static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            String tid = String.valueOf(Thread.currentThread().getId());
            System.out.printf("Thread#%s : in call\n", tid);
            return tid;
        }
    }


    /**
     * 当你调用Future的get()方法以获得结果时，当前线程就开始阻塞，直接call方法结束返回结果。
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++)
            results.add(es.submit(new Task()));

        for (Future<String> res : results)
            System.out.println(res.get());
    }
}
