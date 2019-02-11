package com.java.test;

import com.java.common.jersey.ApiGenerateResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/2/11 11:25
 * @Description:
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "com", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ApiGenerateResource.class))
@ServletComponentScan("com")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }
}
