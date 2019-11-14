package com.lucky.test.abstracttest;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/11 11:31
 * @Description:
 * 抽象类，可以没有抽象方法
 * 但是如果有抽象方法的话，不能有实现
 */
public abstract class A {


    public void position() {
        System.out.println(getX()+getY());
    }
    public abstract int getX();

    public abstract int getY();
    public int func1(int num1, int num2) {
        return num1 - num2;
    }
}
