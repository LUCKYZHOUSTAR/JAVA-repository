package com.lucky.aop.basic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * 之前的切点表达式定义了拦截类中所有方法，所以每个方法都被增强。同时在ApplicationContext中获取的greetingImpl代理对象，可转型为自己静态实现的接口Greeting也可以是实现类GreetingImpl。属性proxy-target-class默认为false，代表只代理接口，也就是说只能将代理转型为Greeting，而不能是GreetingImpl
 * @Date:Create in 19:00 2017/11/30
 */

/**
 * 结果发现，实现类中的实现接口的方法被增强了，而自己创建的good方法没有被增强，这就是因为切点设置为Greeting接口里面所有方法被加强，所以实现了这个接口中的方法被增强了。
 */
@Aspect
@Component
public class AroundAspect {
    @Around("execution(* com.lucky.aop.basic.GreetingImpl.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        before();
        Object result = pjp.proceed();
        after();
        return result;
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("afterr");
    }
}
