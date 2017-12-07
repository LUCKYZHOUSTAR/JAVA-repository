package com.lucky.bean;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:03 2017/12/7
 */

@Service
public class BeanPost {
    @PostConstruct //初始化方法的注解方式  等同与init-method=init
    public void init() {
        System.out.println("调用初始化方法....");
    }

    @PreDestroy //销毁方法的注解方式  等同于destory-method=destory222
    public void destory() {
        System.out.println("调用销毁化方法....");
    }
}
