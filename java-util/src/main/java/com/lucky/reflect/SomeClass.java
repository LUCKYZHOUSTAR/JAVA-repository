package com.lucky.reflect;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:13 2017/12/15
 */
public class SomeClass {
    private String name;

    public void foo(String name) {
        this.name = name;
    }


    public void sayHello(String name) {
        System.out.println(name);
    }

}
