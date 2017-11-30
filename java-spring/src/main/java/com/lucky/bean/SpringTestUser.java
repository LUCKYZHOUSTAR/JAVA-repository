package com.lucky.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:48 2017/11/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:applicationContext.xml"})
public class SpringTestUser {

    @Autowired
    private Life life;

    @Test
    public void test1() {

        System.out.println(life.getName());
    }
}
