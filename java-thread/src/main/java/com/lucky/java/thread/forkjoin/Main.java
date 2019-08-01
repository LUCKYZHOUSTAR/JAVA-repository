package com.lucky.java.thread.forkjoin;

import java.util.stream.LongStream;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/8/1 15:28
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 1000).toArray();
        Calculator calculator = new ForLoopCalculator();
        System.out.println(calculator.sumUp(numbers)); // 打印结果500500
    }

}
