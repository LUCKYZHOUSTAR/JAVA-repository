package com.lucky.ioc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:12 2017/11/30
 */
public class SpringTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("BeforeClass 标注的方法 会最先先被执行");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("AfterClass 标注的方法 会最后执行");
    }

    @Test
    public void test() {

    }

    @Test
    public void test2() {

//        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("F:/workspace/appcontext.xml");
//
//        ApplicationContext ac = new ClassPathXmlApplicationContext("com/xxx/spring/chap1/coll.xml");
//        BeanFactory factory = new ClassPathXmlApplicationContext("com/xxxspring/chap1/ioc.xml");
        BeanFactory factory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //User user = (User) factory.getBean("user");
        //User user = (User) factory.getBean("user",User.class);
        //User user = (User) factory.getBean(User.class); //只有唯一的bean的时候才使用这种方式
        //System.out.println(user);
        System.out.println(factory.getType("user")); //获取user实例的类型
        User user = (User) factory.getBean("user");
        User user2 = (User) factory.getBean("user");
        System.out.println(user == user2);//true -- 单例  --这是可以控制的在配置文件中 bean scope="prototype"-->会变成原型模式 这时结果会是false
        System.out.println(factory.isPrototype("user"));//是否为原型     false
        System.out.println(factory.isSingleton("user"));//是否为单例     true
        System.out.println(factory.isTypeMatch("user", User.class));//判断 user实例是否为这种类型 trussh
        String[] str = factory.getAliases("user"); //获取别名
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);//user2
        }
    }
}
