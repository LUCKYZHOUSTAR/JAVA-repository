package com.lucky.boot;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:41 2017/12/20
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:16 2017/12/12
 * <p>
 * EnableAutoConfiguration:会扫描/META-INF/spring.factories文件中的jar包，
 *
 * EnableAutoConfiguration:会扫描/META-INF/spring.factories文件中的jar包，
 */

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:16 2017/12/12
 * <p>
 * EnableAutoConfiguration:会扫描/META-INF/spring.factories文件中的jar包，
 */

/**
 * EnableAutoConfiguration:会扫描/META-INF/spring.factories文件中的jar包，
 */

import com.lucky.boot.config.DebugConfig;
import com.lucky.boot.flag.ContentService;
import com.lucky.boot.service.FlyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @SpringBootApplication是spring boot框架中的核心注解，通过上面的代码可以看出他是一个组合注解。
 * 组合的注解有：@Configuration、@EnableAutoConfiguration、@ComponentScan。
 * 因此如果我们不直接使用@SpringBootApplication注解，
 * 则可以直接使用@Configuration、@EnableAutoConfiguration、@ComponentScan三个注解。
 */
//默认扫描的就是这个类下面的包和子包,可以手动指定
//@ComponentScan("com.lucky.boot.flag")
@SpringBootApplication
public class BootStrapTest {

    public static void main(String[] args) {
        //System.out.println("sadf");
        ApplicationContext context = SpringApplication.run(BootStrapTest.class, args);
        //context.getBean(FlyService.class).fly();
        //context.getBean(ContentService.class).doSomething();

       DebugConfig debugConfig= context.getBean(DebugConfig.class);
        System.out.println(debugConfig);
    }
}
