package com.lucky.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:42 2017/11/30
 */
public class SpringTest {


    /**
     * 调用无参构造器
     * 调用setName方法
     * 调用setBeanName方法
     * 调用setBeanFactory方法
     * 调用myInit方法
     * com.briup.spring.ioc.bean.Life@4f0b5b
     * 调用myDestory方法
     * AfterClass 标注的方法 会最后执行
     */
    @Test
    public void test2() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-life.xml");

        Life life = ac.getBean(Life.class);
        /*在xml文件中指明destory-method需要执行的方法后，bean生命周期并不会自动去掉用myDestory方法，需要ac.detstory(),才会调用*/
        ac.destroy();
    }


    /**
     * spring中提供了许多已Aware结尾的类，这些类可以获取容器中的一些资源
     * 比如ApplicationContextAware，可以获取applicationCcontext中的内容
     * BeanNameAware可以获取到Bean的beanNam
     */
    @Test
    public void test3() {
        //该bean初始化后，才会调用application的操作
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        AwareTest awareTest = ac.getBean("applicationAawareTest", AwareTest.class);
        System.out.println(awareTest);
    }
}
