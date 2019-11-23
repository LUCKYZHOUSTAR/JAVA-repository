package com.lucky.test.pk2;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/14 17:00
 * @Description:
 */
public class FlyImpl extends AbsFly {


    @Override
    public void fly() {
        System.out.println("Flsyimo;");
    }

    public static void main(String[] args) {
        AbsFly fly = new AbsFly() {

            @Override
            public void eat() {
                System.out.println("我是子类，抽象类，我吃了");
            }

            @Override
            public void print() {
                System.out.println("我把接口的信息也给打印了");

            }


        };

        fly.eat();
        fly.print();


        AbsFly absFly = new FlyImpl();

        absFly.fly();
    }
}
