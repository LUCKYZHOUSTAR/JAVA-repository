package com.lucky.test.pk2;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/14 16:58
 * @Description:如果是类的话，必须要实现全部接口的方法，但是 如果是抽象类的话，就可以作为一个中转层
 */
public abstract class AbsFly implements IFly {


    //可以有选择的实现，接口里面的方法
    @Override
    public void fly() {

    }

    @Override
    public void eat() {

    }
}
