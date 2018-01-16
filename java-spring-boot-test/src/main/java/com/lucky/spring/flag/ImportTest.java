package com.lucky.spring.flag;

import com.lucky.spring.service.FlyService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:22 2017/12/12
 */

//该标签可以自动的注入类信息,也可以装在类信息
@Import(FlyService.class)
@Configuration
public class ImportTest {


    /**
     * 1.最简单的方法，当然是把它放到程序可以扫描到的package里，也就是@ComponentScan注解所指定的package里。
     平时自己创建的配置类通常用这种方法，简单明了。
     2.如果没有在package扫描路径里，比如引入的第三方包，可以通过META-INF/spring.factories里用org.springframework.boot.autoconfigure.EnableAutoConfiguration来制定。
     spring-boot-autoconfigure包里的配置类都是通过这种方式引入的。
     当然，这个方式需要程序使用@EnableAutoConfiguration注解，这个注解是通过AutoConfigurationImportSelector来扫描spring.factories文件，把定义的配置类引入的。
     3.使用@Import注解
     这个注解可以引入三种类
     a.使用了@Configuration注解的类
     这个比较简单，如果明确知道需要引入哪个配置类，直接引入就可以。
     b.ImportSelector的子类
     如果并不确定引入哪个配置类，需要根据@Import注解所标识的类或者另一个注解(通常是注解)里的定义信息选择配置类的话，用这种方式。
     实际上上面2种所描述的AutoConfigurationImportSelector就是用的这种方式。
     另外一个比较典型的是注解@EnableTransactionManagement
     */
}
