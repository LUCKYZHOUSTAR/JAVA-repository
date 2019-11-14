package com.lucky.test.abstracttest;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/11 11:32
 * @Description:l
 */
public class B extends A {

    //子类把父类的属性给重载了，重写了
    @Override
    public int func1(int num1, int num2) {

        return num1 + num2;
    }

    @Override
    public int getX() {
        return 1;
    }

    @Override
    public int getY() {
        return 2;
    }

    public static void main(String[] args) {
        A a = new B();

        a.position();
    }
}
