package com.lucky.aop.basic;

import org.springframework.stereotype.Service;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:29 2017/11/30
 */
@Service
public class GreetingImpl implements Greeting {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello:" + name);
    }


    public void goodMoring(String name) {
        System.out.println("Good Moring" + name);
    }


}
