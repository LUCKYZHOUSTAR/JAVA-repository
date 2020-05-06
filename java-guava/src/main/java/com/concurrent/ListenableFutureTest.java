package com.concurrent;

import com.google.common.util.concurrent.*;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:26 2017/11/17
 */
public class ListenableFutureTest {
    /**
     * 1、MoreExecutors
     * <p>
     * 该类是final类型的工具类，提供了很多静态方法。例如listeningDecorator方法初始化ListeningExecutorService方法，使用此实例submit
     * 方法即可初始化ListenableFuture对象。
     * <p>
     * 2、ListeningExecutorService
     * <p>
     * 该类是对ExecutorService的扩展，重写ExecutorService类中的submit方法，返回ListenableFuture对象。
     * <p>
     * 3、ListenableFuture
     * <p>
     * 该接口扩展了Future接口，增加了addListener方法，该方法在给定的excutor上注册一个监听器，当计算完成时会马上调用该监听器。不能够确保监听器执行的顺序，但可以在计算完成时确保马上被调用。
     * <p>
     * 4、FutureCallback
     * <p>
     * 该接口提供了OnSuccess和OnFailuren方法。获取异步计算的结果并回调。
     * <p>
     * 5、Futures
     * <p>
     * 该类提供和很多实用的静态方法以供使用。
     * <p>
     * 6、ListenableFutureTask
     * <p>
     * 该类扩展了FutureTask类并实现ListenableFuture接口，增加了addListener方法。
     */
    // 创建线程池
    final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4,
        createThreadFactory("ScheduledExecutor pool"));


    private static ThreadFactory createThreadFactory(String name) {
        return new ThreadFactoryBuilder()
            .setPriority(Thread.NORM_PRIORITY)
            .setNameFormat("   GEI-" + name + " - %s")
            .setUncaughtExceptionHandler((t, e) -> System.out.println("2323")).build();
    }
    public static void main(String[] args) throws Exception {
        Long t1 = System.currentTimeMillis();
        // 任务1
        ListenableFuture<Boolean> booleanTask = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        //可以在future基础上套用一层超时的futures
        ListenableFuture<Boolean> timeoutFuture = Futures.withTimeout(booleanTask, 1000, TimeUnit.MILLISECONDS,
            executor);

        //还可以为future添加回调函数
        Futures.addCallback(timeoutFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });



        booleanTask.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("get listenable future's result " + booleanTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, service);
        Futures.addCallback(booleanTask, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                System.err.println("BooleanTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        // 任务2
        ListenableFuture<String> stringTask = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello World";
            }
        });

        Futures.addCallback(stringTask, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("StringTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        // 任务3
        ListenableFuture<Integer> integerTask = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });

        Futures.addCallback(integerTask, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("IntegerTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        // 执行时间
        System.err.println("time: " + (System.currentTimeMillis() - t1));
    }

}
