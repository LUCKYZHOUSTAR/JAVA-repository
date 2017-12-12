package com.lucky.boot.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:24 2017/12/12
 */
@Slf4j
public class HelloService {

    public void sayHello() {
        System.out.println("注册bean测试");
    }


    public void start() {
        //private  final Logger logger = LoggerFactory.getLogger(XXX.class);
        log.info("自动的开始log日志信息");
        System.out.println("我开始了");
    }

    public void stop() {
        System.out.println("我结束了");
    }
}
