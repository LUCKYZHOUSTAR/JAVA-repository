package com.lucky.cglib;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/18 10:26
 * @Description:
 */
public class Point1 implements Chain.Point {

    @Override
    public Object proceed(Chain chain) {
        try {
            System.out.println("point 1 before");
            Thread.sleep(20);
            Object result = chain.proceed();
            Thread.sleep(20);
            System.out.println("point 1 after");
            return result;
        } catch (InterruptedException e) {

        }

        return null;
    }
}
