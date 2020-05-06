package com.lucky.boot.service;

import org.springframework.stereotype.Component;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/1/23 16:49
 * @Description:
 */
@Component
public class FlyServiceTest {

    public FlyServiceTest(HelloService helloService) {
        System.out.println("走我了吗");
    }

    public FlyServiceTest() {
    }
}
