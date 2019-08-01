package com.lucky.java.thread.forkjoin;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/8/1 15:28
 * @Description:
 */
public class ForLoopCalculator implements Calculator {
    public long sumUp(long[] numbers) {
        long total = 0;
        for (long i : numbers) {
            total += i;
        }
        return total;
    }
}