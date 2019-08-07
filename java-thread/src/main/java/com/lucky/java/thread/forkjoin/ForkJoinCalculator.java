package com.lucky.java.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/8/1 15:31
 * @Description:http://blog.dyngr.com/blog/2016/09/15/java-forkjoinpool-internals/ 下面来详细说明工作窃取算法(模拟)：
 * <p>
 * 有一个较大的任务划分成了10个小任务。
 * 这10个小任务在一个大小为2的线程池中执行。
 * 线程池中的2个核心线程，每个线程的队列中有5个任务。
 * 线程1的任务都很简单，所以它很快就将5个任务执行完毕。
 * 线程2的任务都很复杂，当线程1执行完5个任务时，他才执行了3个任务。
 * 这时，线程1不会空闲，而且窃取线程2的等待队列中的任务(从末端开始窃取)来执行。
 * 当线程2的队列中也没有了任务之后，线程1和线程2才空闲。
 * 优缺点
 * <p>
 * 整体上，这种窃取算法，提高了线程利用率。
 * 为了减少窃取任务线程和被窃取任务线程之间的竞争，通常会使用双端队列。
 * 存在两个线程共同竞争同一个任务的可能，例如双端队列中只有一个任务时。
 */
public class ForkJoinCalculator implements Calculator {
    private ForkJoinPool pool;


    public static void main(String[] args) {
            //构造函数
            //无参：并行级别=Runtime.getRuntime.availableProcessors();
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            //指定并行级别
            ForkJoinPool forkJoinPool1 = new ForkJoinPool(4);

            //提交任务(返回计算情况)
            //ForkJoinTask<V> implements Future<V>, Serializable
            //提交Runnable任务
            Runnable runnable = null;
            forkJoinPool.submit(runnable);
            //提交Runnable + result任务
            Integer result = null;
            Future<Integer> future2 = forkJoinPool.submit(runnable, result);
            //提交Callable<V>任务
            Callable<Integer> callable = null;
            Future<Integer> future3 = forkJoinPool.submit(callable);
            //提交ForkJoinTask<V>任务
            ForkJoinTask<Integer> forkJoinTask = null;
            Future<Integer> future4 = forkJoinPool.submit(forkJoinTask);
            //提交RecursiveAction任务(RecursiveAction extends ForkJoinTask<Void>)
            RecursiveAction recursiveAction = null;
            forkJoinPool.submit(recursiveAction);
            //提交RecursiveTask<V>任务(RecursiveTask<V> extends ForkJoinTask<V>)
            RecursiveTask<Integer> recursiveTask = null;
            Future<Integer> future6 = forkJoinPool.submit(recursiveTask);

            //提交任务(不返回计算情况)
            //提交Runnable任务
            Runnable runnable1 = null;
            forkJoinPool.execute(runnable1);
            //提交ForkJoinTask<V>任务
            ForkJoinTask<Integer> forkJoinTask1 = null;
            forkJoinPool.execute(forkJoinTask);
            //提交RecursiveAction任务(RecursiveAction extends ForkJoinTask<Void>)
            RecursiveAction recursiveAction1 = null;
            forkJoinPool.execute(recursiveAction);
            //提交RecursiveTask<V>任务(RecursiveTask<V> extends ForkJoinTask<V>)
            RecursiveTask<Integer> recursiveTask1 = null;
            forkJoinPool.execute(recursiveTask);

            //提交任务(返回计算结果)
            //提交ForkJoinTask<V>任务
            ForkJoinTask<Integer> forkJoinTask2 = null;
            Integer result1 = forkJoinPool.invoke(forkJoinTask);
            //提交RecursiveAction任务(RecursiveAction extends ForkJoinTask<Void>)
            RecursiveAction recursiveAction2 = null;
            forkJoinPool.invoke(recursiveAction);
            //提交RecursiveTask<V>任务(RecursiveTask<V> extends ForkJoinTask<V>)
            RecursiveTask<Integer> recursiveTask2 = null;
            Integer result3 = forkJoinPool.invoke(recursiveTask);

            //提交任务集
            //获取最先计算完成的-阻塞
            List<Callable<Integer>> callableList = new ArrayList<Callable<Integer>>();
            try {
                Integer result4 = forkJoinPool.invokeAny(callableList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //获取最先计算完成的-阻塞-可超时
            try {
                Integer result5 = forkJoinPool.invokeAny(callableList, 1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            //所有任务计算完成之后，返回结果-阻塞
            List<Future<Integer>> futureList = forkJoinPool.invokeAll(callableList);
            //所有任务计算完成之后，返回结果-阻塞-可超时
            try {
                List<Future<Integer>> futureList1 = forkJoinPool.invokeAll(callableList, 1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //是否正在终止
            forkJoinPool.isTerminating();
            //是否终止
            forkJoinPool.isTerminated();
            try {
                //等待终止
                forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //是否休眠
            forkJoinPool.isQuiescent();
            //等待休眠
            forkJoinPool.awaitQuiescence(1, TimeUnit.SECONDS);

            //存在等待执行的子任务
            forkJoinPool.hasQueuedSubmissions();

            //是否是FIFO模式
            boolean asyncMode = forkJoinPool.getAsyncMode();
            //获取当前活跃线程数
            int activeThreadCount = forkJoinPool.getActiveThreadCount();
            //获取线程池并行级别
            int parallelism = forkJoinPool.getParallelism();
            //获取工作线程数量
            int poolSize = forkJoinPool.getPoolSize();
            //获取等待执行的子任务数量
            int queuedSubmissionCount = forkJoinPool.getQueuedSubmissionCount();
            //获取等待执行的任务数量
            long queuedTaskCount = forkJoinPool.getQueuedTaskCount();
            //获取非阻塞的活动线程数量
            int runningThreadCount = forkJoinPool.getRunningThreadCount();
            //获取窃取线程数量
            long stealCount = forkJoinPool.getStealCount();
            //获取工作线程工厂
            ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory = forkJoinPool.getFactory();
            //获取未捕获异常处理器
            Thread.UncaughtExceptionHandler handler = forkJoinPool.getUncaughtExceptionHandler();

            //关闭线程池
            forkJoinPool.isShutdown();
            forkJoinPool.shutdown();
            forkJoinPool.shutdownNow();
    }

    private static class SumTask extends RecursiveTask<Long> {
        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            // 当需要计算的数字小于6时，直接计算结果
            if (to - from < 6) {
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
                // 否则，把任务一分为二，递归计算
            } else {
                int middle = (from + to) / 2;
                SumTask taskLeft = new SumTask(numbers, from, middle);
                SumTask taskRight = new SumTask(numbers, middle + 1, to);
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join() + taskRight.join();
            }
        }
    }

    public ForkJoinCalculator() {
        // 也可以使用公用的 ForkJoinPool：
        // pool = ForkJoinPool.commonPool()
        pool = new ForkJoinPool();
    }

    @Override
    public long sumUp(long[] numbers) {
        return pool.invoke(new SumTask(numbers, 0, numbers.length - 1));
    }
}