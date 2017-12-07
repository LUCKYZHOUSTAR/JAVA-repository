package com.lucky.resource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:42 2017/11/30
 */
public class SpringTest {


    /**
     * getResource中location的写法有如下几种
     * prefix前缀  案例     说明
     * classpath:  classpath:com/briup/spring/chap2/life.xml 从classpath中加载
     * file： file:/data/life.xml用URL从文件系统中加载
     * http: http://myserver/logoo.png通过URL从网络加载
     * （none）  /spring/chap2/life.xml 这种相对路径的写法依赖于ApplicationContext
     * <p>
     * spring中的使用
     * Resource template = ctx.getResource("some/resource/path/myTemplate.txt");
     * Resource template = ctx.getResource("classpath:some/resource/path/myTemplate.txt");
     * Resource template = ctx.getResource("file:some/resource/path/myTemplate.txt");
     */
    @Test
    public void test3() {
        //该bean初始化后，才会调用application的操作
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ResourceTest resourceTest = ac.getBean(ResourceTest.class);
        //只有执行了这句话，才会调用distroy方法
        ac.registerShutdownHook();
        try {
            resourceTest.resource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
