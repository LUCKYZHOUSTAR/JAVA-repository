package com.lucky.test.pk2;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/14 16:56
 * @Description:
 */
public interface IFly {

    void fly();

    void eat();

    default void print(){
        System.out.println("我是一只小鸟");
    }
}
