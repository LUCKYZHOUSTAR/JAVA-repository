package com.lucky.aop.basic;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author:chaoqiang.zhou
 * @Description:环绕增强类需要实现 org.aopalliance.intercept.MethodInterceptor 接口。注意，这个接口不是 Spring 提供的，它是 AOP 联盟写的，Spring 只是借用了它。
 * @Date:Create in 18:35 2017/11/30
 * 环绕增强（当把两个接口合并时，其实完全可以用一个接口就行）
 */
public class GreetingAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        before();
        System.out.println(methodInvocation.getMethod().getName());
        System.out.println(methodInvocation.getArguments());
        Object result = methodInvocation.proceed();
        after();
        return result;
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
