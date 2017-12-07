package com.lucky.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Author:chaoqiang.zhou
 * @Description:bean的初始化和销毁
 * http://blog.csdn.net/luckey_zh/article/details/46673969
 * 或者通过init-方法或者destroy方法来指定
 * @Date:Ceate in 10:58 2017/12/7
 */
@Component
public class InitializingBeanTest implements InitializingBean, DisposableBean {

    //初始化
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet运行了");
    }

    //销毁
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy运行了");
    }
}
