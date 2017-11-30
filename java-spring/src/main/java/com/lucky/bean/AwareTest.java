package com.lucky.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:51 2017/11/30
 */
@Service
public class AwareTest implements ApplicationContextAware, BeanNameAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext.getBean(AwareTest.class));
        System.out.println(applicationContext.getBean(Life.class));

    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println(beanName);
    }
}
