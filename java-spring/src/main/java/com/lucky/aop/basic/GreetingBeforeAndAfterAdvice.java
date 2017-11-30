package com.lucky.aop.basic;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author:chaoqiang.zhou
 * @Description:编写前置增强和后置增强（这里我将两个增强合并，即实现两个接口）
 * https://www.cnblogs.com/zhaozihan/p/5953063.html
 * @Date:Create in 18:29 2017/11/30
 */
public class GreetingBeforeAndAfterAdvice implements MethodBeforeAdvice, AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("After");
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("befoe");
        System.out.println(method.getName());
        System.out.println(objects);
        System.out.println("代理对象是" + o);
    }
}
