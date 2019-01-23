package com.lucky.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:25 2017/12/12
 */
@Configuration
public class FlyService {

//
//    public FlyService(HelloService helloService) {
//        System.out.println("有参数的构造函数");
//    }

    public void fly() {
        System.out.println("开始fly操作");
    }
}
