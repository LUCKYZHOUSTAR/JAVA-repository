package com.lucky.jctool;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/8/12 18:16
 * @Description:
 */
public class ThreadTest {

    public static void main(String[] args) {

        int i = 0;
        while (true) {

            i++;
            System.out.println(i);
            if (i % 10000 == 0) {
                LockSupport.park();
            }
        }
    }
}
