package com.lucky.java.thread;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

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
//        thenApplyAsyncWithExecutorExample();

//        completeExceptionallyExample();

//        cancelExample();

//        applyToEitherExample();
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

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName());

            }
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
     * @param:1.执行一个异步的任务 2.创建了一个分离的hander阶段，用来处理异常，在异常的情况下手动返回了message upon cancel
     * 3、显示的阻断异常，抛出一个CompletionException，
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午12:02
     */
//    static void completeExceptionallyExample() {
//        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
//                CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS));
//        CompletableFuture exceptionHandler = cf.handle((s, th) -> {
//            return (th != null) ? "message upon cancel" : "";
//        });
//        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
//        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
//        try {
//            cf.join();
//            fail("Should have thrown an exception");
//        } catch (CompletionException ex) { // just for testing
//            assertEquals("completed exceptionally", ex.getCause().getMessage());
//        }
//
//        assertEquals("message upon cancel", exceptionHandler.join());
//
//
//    }


//    static void cancelExample() {
//        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
//                    for (int i = 0; i < 2; i++) {
////                        sleepEnough();
//                        System.out.println(i);
//                    }
//                    return "测试信息";
//                },
//                CompletableFuture.delayedExecutor(0, TimeUnit.SECONDS));
//
//        CompletableFuture cf2 = cf.exceptionally(throwable -> "canceled message");
//        sleepEnough();
//        try {
//            //这种方法，只是把结果set了一个异常的结果，当调用join的时候，就判断结果的类型，如果是异常就直接抛出来了，那么在执行异常之前，上述的过程是否终止呢
//            //经过测试当执行exceptional后，只是把结果给set了，result设置了一个异常值，中间的异步过程，还是会执行的，只不过join的时候，会报出异常
//            //最终的result值，会设置两次
//            System.out.println(cf.cancel(true));
//        } catch (CancellationException e) {
//            System.out.println(e.getMessage());
//        }
////        System.out.println(cf.isCompletedExceptionally());
////        System.out.println(cf.isCancelled());
////        assertTrue("Was not canceled", cf.cancel(true));
////        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
//        sleepEnough();
//        //该值可能是canceled message ，也可能是测试信息，看谁先设置了
//        System.out.println(cf2.getNow("2323"));
////        cf.join();
////        assertEquals("canceled message", cf.join());
//    }


    /**
     * 功能描述:
     *
     * @param: 处理两个阶段，一个阶段完了，才开始另一个阶段
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午2:54
     */
    static void applyToEitherExample() {
        String original = "Message";
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s));
        CompletableFuture<String> cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                s -> s + " from applyToEither");
        assertTrue(cf2.join().endsWith(" from applyToEither"));
    }


    /**
     * 功能描述:
     *
     * @param: 和前一个例子很类似了，只不过我们调用的是消费者函数 (Function变成Consumer):
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午2:55
     */
    static void acceptEitherExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                        s -> result.append(s).append("acceptEither"));
        cf.join();
        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
    }


    /**
     * 功能描述:
     *
     * @param: 两个阶段都完成后，做一个输出的操作
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午2:56
     */
    static void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        assertTrue("Result was empty", result.length() > 0);
    }


    /**
     * 功能描述:
     *
     * @param: 上面的例子还可以通过BiConsumer来实现:
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午3:00
     */
    static void thenAcceptBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1 + s2));
        assertEquals("MESSAGEmessage", result.toString());
    }


    /**
     * 功能描述:
     *
     * @param: 如果CompletableFuture依赖两个前面阶段的结果， 它复合两个阶段的结果再返回一个结果，我们就可以使用thenCombine()函数。整个流水线是同步的，所以getNow()会得到最终的结果，它把大写和小写字符串连接起来。
     * @return:
     * @auther: zhou
     * @date: 2019/8/1 下午3:02
     */
    static void thenCombineExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                .thenCombine(CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.getNow(null));
    }

    //异步的联合执行结果信息
    static void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.join());
    }


    /**
     * 功能描述:
     *
     * @param: 用来传递结果
     * @return: 我们可以使用thenCompose()完成上面两个例子。这个方法等待第一个阶段的完成(大写转换)， 它的结果传给一个指定的返回CompletableFuture函数，它的结果就是返回的CompletableFuture的结果。
     * @auther: zhou
     * @date: 2019/8/1 下午3:20
     */
    static void thenComposeExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s))
                        .thenApply(s -> upper + s));
        assertEquals("MESSAGEmessage", cf.join());
    }


    static void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if (th == null) {
                assertTrue(isUpperCase((String) res));
                result.append(res);
            }
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    static void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
            result.append("done");
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    static void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
                    result.append("done");
                });
        allOf.join();
        assertTrue("Result was empty", result.length() > 0);
    }


    private static boolean isUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String delayedUpperCase(String s) {
        randomSleep();
        return s.toUpperCase();
    }

    private static String delayedLowerCase(String s) {
        randomSleep();
        return s.toLowerCase();
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
