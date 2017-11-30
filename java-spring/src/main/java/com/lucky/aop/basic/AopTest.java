package com.lucky.aop.basic;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.awt.geom.AreaOp;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:31 2017/11/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:applicationContext-aop.xml"})
public class AopTest {


    @Test
    public void test1() {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetingImpl());
        proxyFactory.addAdvice(new GreetingBeforeAndAfterAdvice());
        Greeting greeting = (Greeting) proxyFactory.getProxy();
        greeting.sayHello("jack");
    }


    @Test
    public void test2() {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetingImpl());
        proxyFactory.addAdvice(new GreetingAroundAdvice());

        Greeting greeting = (Greeting) proxyFactory.getProxy();
        greeting.sayHello("jack");
    }

    @Test
    public void test3() {

        ProxyFactoryBean proxyFactoryBean=new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new GreetingImpl());
        proxyFactoryBean.addAdvice(new GreetingBeforeAndAfterAdvice());
        Greeting greeting = (Greeting) proxyFactoryBean.getObject();
        greeting.sayHello("jack");
    }

    @Test
    public void test4() {

        RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor= new RegexpMethodPointcutAdvisor();
        regexpMethodPointcutAdvisor.setAdvice(new GreetingAroundAdvice());
        regexpMethodPointcutAdvisor.setPattern("com.lucky.aop.basic.GreetingImpl.good.*");


        ProxyFactoryBean proxyFactoryBean=new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new GreetingImpl());

        proxyFactoryBean.addAdvisors(regexpMethodPointcutAdvisor);
//        proxyFactoryBean.setInterceptorNames("regexpMethodPointcutAdvisor");
        proxyFactoryBean.setProxyTargetClass(true);//代理目标累
        GreetingImpl greeting = (GreetingImpl) proxyFactoryBean.getObject();
        greeting.sayHello("jack");
        greeting.goodMoring("jack");
    }



    @Autowired
    private Greeting greeting;

    @Test
    public void test5(){
        greeting.sayHello("ja");

    }
}
