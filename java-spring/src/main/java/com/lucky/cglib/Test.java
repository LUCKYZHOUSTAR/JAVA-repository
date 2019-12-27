package com.lucky.cglib;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/18 10:29
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        Object proxy = ProxyFactory.create().getProxy(new SayHello());
        proxy.toString();
    }


    static class SayHello {

        @Override
        public String toString() {
            return "hello cglib !";
        }
    }
}
