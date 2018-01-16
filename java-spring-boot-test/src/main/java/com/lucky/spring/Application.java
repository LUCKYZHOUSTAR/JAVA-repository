package com.lucky.spring;//package com.lucky.boot;
//
//import com.lucky.boot.flag.ContentService;
//import com.lucky.boot.service.FlyService;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:
// * @Date:Create in 14:16 2017/12/12
// * <p>
// * EnableAutoConfiguration:会扫描/META-INF/spring.factories文件中的jar包，
// */
//
//
///**
// * EnableAutoConfiguration:会扫描/META-INF/spring.factories文件中的jar包，
// */
//
///**
// * @SpringBootApplication是spring boot框架中的核心注解，通过上面的代码可以看出他是一个组合注解。
// * 组合的注解有：@Configuration、@EnableAutoConfiguration、@ComponentScan。
// * 因此如果我们不直接使用@SpringBootApplication注解，
// * 则可以直接使用@Configuration、@EnableAutoConfiguration、@ComponentScan三个注解。
// */
////默认扫描的就是这个类下面的包和子包,可以手动指定
//@SpringBootApplication
//@ComponentScan("com.lucky.boot.flag")
//public class Application {
//
//    public static void main(String[] args) {
//        System.out.println("HEELO");
//    }
////    public static void main(String[] args) {
////        ApplicationContext context = SpringApplication.run(Application.class, args);
////        context.getBean(FlyService.class).fly();
////        context.getBean(ContentService.class).doSomething();
////    }
//}
