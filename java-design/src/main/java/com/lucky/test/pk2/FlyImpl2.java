package com.lucky.test.pk2;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/21 14:08
 * @Description:
 */
public class FlyImpl2 extends FlyImpl {

    @Override
    public void eat() {
        System.out.println("i am FlyImpl2 to eat");
    }


    public static void main(String[] args) {
        IFly iFly = new FlyImpl2();

        iFly.eat();
    }
}
