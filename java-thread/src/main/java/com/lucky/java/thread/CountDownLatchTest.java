package com.lucky.java.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author:chaoqiang.zhou
 * @Description:　计数器必须大于等于0，只是等于0时候，计数器就是零，调用await方法时不会 阻塞当前线程。CountDownLatch不可能重新初始化或者修改CountDownLatch对象的内部计数
 * 器的值。一个线程调用countDown方法happen-before，另外一个线程调用await方法。
 * @Date:Create in 19:17 2017/11/28
 */
public class CountDownLatchTest {
    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();
            }
        }).start();


        c.await();
        System.out.println("3");
    }
}
