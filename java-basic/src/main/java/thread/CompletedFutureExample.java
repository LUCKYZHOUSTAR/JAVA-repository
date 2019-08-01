package thread;

import java.util.Random;
import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/8/1 11:27
 * @Description:
 */
public class CompletedFutureExample {

    static Random random = new Random();


    static ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "custom-executor-" + count++);
        }
    });

    public static void main(String[] args) {
//        completedFutureExample();

//        runAsyncExample();

//        thenApplyExample();
//        thenApplyAsyncExample();
        thenApplyAsyncWithExecutorExample();
    }

    static void completedFutureExample() {

        CompletableFuture cf = CompletableFuture.completedFuture("message");

        System.out.println(cf.isDone());
        System.out.println(cf.getNow(null));


    }


    /**
     * 功能描述:
     *
     * @param: CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)， 异步执行通过ForkJoinPool实现， 它使用守护线程去执行任务。注意这是CompletableFuture的特性， 其它CompletionStage可以override这个默认的行为。
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 上午11:38
     */
    static void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            //守护线程去执行
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
        });
        assertFalse(cf.isDone());
        sleepEnough();
        assertTrue(cf.isDone());
    }


    /**
     * 功能描述:
     *
     * @param: then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
     * <p>
     * Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
     * <p>
     * 函数的执行会被阻塞，这意味着getNow()只有打斜操作被完成后才返回。
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 上午11:45
     */
    static void thenApplyExample() {
        //守护线程执行，getNow是阻塞的，只有返回结果的时候，才释放，这个是同步函数，同步来执行
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            assertFalse(Thread.currentThread().isDaemon());
            sleepEnough();
            sleepEnough();
            sleepEnough();
            return s.toUpperCase();
        });

        System.out.println(cf.join());
        assertEquals("MESSAGE", cf.getNow(null));
        System.out.println("结束了吗");
    }


    /**
     * 功能描述:
     *
     * @param:通过调用异步方法(方法后边加Async后缀)，串联起来的CompletableFuture可以异步地执行（使用ForkJoinPool.commonPool()）。
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 上午11:46
     */
    static void thenApplyAsyncExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
            randomSleep();
            randomSleep();

            return s.toUpperCase();
        });

        //同步结果，阻塞获取结果
        System.out.println(cf.join());
        //是非阻塞的，如果完成了，直接返回结果，否则返回给定的值
        assertNull(cf.getNow(null));
        System.out.println(cf.getNow(null));
        System.out.println(cf.join());
        assertEquals("MESSAGE", cf.join());
    }


    /**
     * 功能描述:
     *
     * @param: 手动指定线程池，就不是守护线程了，看自己制定的
     * 异步方法一个非常有用的特性就是能够提供一个Executor来异步地执行CompletableFuture。这个例子演示了如何使用一个固定大小的线程池来应用大写函数。
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 上午11:59
     */
    static void thenApplyAsyncWithExecutorExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));

            assertFalse(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();

        }, executor);

        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }


    /**
     * 功能描述:
     *
     * @param: 如果下一阶段接收了当前阶段的结果，但是在计算的时候不需要返回值(它的返回类型是void)， 那么它可以不应用一个函数，而是一个消费者， 调用方法也变成了thenAccept:
     * @return:本实例是同步的执行，不需要join方法来进行阻塞
     * @auther: zhou
     * @date: 2019/8/1 下午12:00
     */
    static void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> result.append(s));
        assertTrue("Result was empty", result.length() > 0);
    }


    /**
     * 功能描述:
     *
     * @param: 异步的消费迁移阶段
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午12:01
     */
    static void thenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s -> result.append(s));

        //阻塞的函数
        cf.join();
        assertTrue("Result was empty", result.length() > 0);
    }


    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午12:02
     */
    static void completeExceptionallyExample() {

        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));


    }


    private static void randomSleep() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            // ...
        }
    }

    private static void sleepEnough() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ...
        }
    }

}
