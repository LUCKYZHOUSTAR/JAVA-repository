package com.lucky.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:40 2017/11/30
 */
@Service
public class Life implements BeanNameAware,BeanFactoryAware {
    private String name;

    public Life(){//一加载就会调到用
        System.out.println("调用无参构造器");
    }

    public String getName() {
        return name;
    }

    public Life(String name) {
        System.out.println("开始走有参数的构造函数");
    }
    public void setName(String name) {
        System.out.println("调用setName方法");
        this.name = name;
    }


    public void myInit() {
        System.out.println("调用myInit方法");
    }

    public void myDestory(){
        System.out.println("调用myDestory方法");
    }

    @Override
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        System.out.println("调用setBeanFactory方法");

    }

    @Override
    public void setBeanName(String arg0) {
        System.out.println("调用setBeanName方法");
    }
}
