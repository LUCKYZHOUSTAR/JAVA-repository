package com.lucky.aop.basic;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:44 2017/11/30
 */
public class LoveAdvice extends DelegatingIntroductionInterceptor implements Love {
    @Override
    public void display(String name) {

        System.out.println("hello" + name);
    }


    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        return super.invoke(mi);
    }
}
