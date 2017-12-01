package com.lucky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:26 2017/12/1
 */
/*@Configuration
@EnableAutoConfiguration
@ComponentScan*/
@SpringBootApplication
public class Application {
    //复习servlet，不想用tomact了，直接用springboot内嵌tomact组件，来启动一个web
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
