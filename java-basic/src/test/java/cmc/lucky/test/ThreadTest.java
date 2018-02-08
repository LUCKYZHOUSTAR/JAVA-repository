package cmc.lucky.test;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:49 2018/1/19
 */
public class ThreadTest {

    public static void main(String[] args) {
        for (; ; ) {
            System.out.println("哈哈");
            //并没有中断，只是让出cpu
//            Thread.yield();
            LockSupport.park(ThreadTest.class);

        }
    }
}


