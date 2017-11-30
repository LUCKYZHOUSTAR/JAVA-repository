package com.lucky.resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:01 2017/11/30
 */
@Service
public class ResourceTest implements ApplicationContextAware {
    private ApplicationContext ApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ApplicationContext = applicationContext;
    }

    public void resource() throws IOException {
        //Resource resource = ApplicationContext.getResource("config.txt");//默认为classpath
        //Resource resource = ApplicationContext.getResource("classpath:config.txt");
        //Resource resource = ApplicationContext.getResource("file:D:\\workspace\\xnxy_spring\\src\\config.txt");
        Resource resource = ApplicationContext.getResource("url:http://repo.springsource.org/libs-release-local/org/springframework/spring/3.2.4.RELEASE/spring-framework-3.2.4.RELEASE-dist.zip");
        System.out.println(resource.getFilename());//获取文件名
        System.out.println(resource.contentLength()); //获取文件长度
        System.out.println(resource.getInputStream());//获取输入流
    }
}
