package com.lucky.spring.flag;

import com.lucky.spring.service.HelloService;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;


/**
 * 本文参考自：https://www.cnblogs.com/javapath/p/6814016.html
 *
 * @Author:chaoqiang.zhou
 * @Description:条件注解@Conditional，组合注解，元注解
 * @Conditional：满足特定条件创建一个Bean，SpringBoot就是利用这个特性进行自动配置的。 例子:
 * <p>
 * 首先，两个Condition，判断当前系统是否是Windows或者Linux（True False）
 * <p>
 * 然后，2个ListService实现类，表明不同系统下的ListService实现。
 * <p>
 * 主要，ConditionConfig使用了Java配置与@Conditional注解，根据LinuxCondition，或者WindowsCondition作为判断条件
 * <p>
 * 产生相应与系统匹配的实现类。
 * @Date:Create in 14:38 2017/12/12
 * @Author:chaoqiang.zhou
 * @Description:条件注解@Conditional，组合注解，元注解
 * @Conditional：满足特定条件创建一个Bean，SpringBoot就是利用这个特性进行自动配置的。 例子:
 * <p>
 * 首先，两个Condition，判断当前系统是否是Windows或者Linux（True False）
 * <p>
 * 然后，2个ListService实现类，表明不同系统下的ListService实现。
 * <p>
 * 主要，ConditionConfig使用了Java配置与@Conditional注解，根据LinuxCondition，或者WindowsCondition作为判断条件
 * <p>
 * 产生相应与系统匹配的实现类。
 * @Date:Create in 14:38 2017/12/12
 */
/**
 * @Author:chaoqiang.zhou
 * @Description:条件注解@Conditional，组合注解，元注解
 * @Conditional：满足特定条件创建一个Bean，SpringBoot就是利用这个特性进行自动配置的。 例子:
 * <p>
 * 首先，两个Condition，判断当前系统是否是Windows或者Linux（True False）
 * <p>
 * 然后，2个ListService实现类，表明不同系统下的ListService实现。
 * <p>
 * 主要，ConditionConfig使用了Java配置与@Conditional注解，根据LinuxCondition，或者WindowsCondition作为判断条件
 * <p>
 * 产生相应与系统匹配的实现类。
 * @Date:Create in 14:38 2017/12/12
 */

/**
 * spring.factories文件里每一个xxxAutoConfiguration文件一般都会有下面的条件注解:

 @ConditionalOnBean：当容器里有指定Bean的条件下

 @ConditionalOnClass：当类路径下有指定类的条件下

 @ConditionalOnExpression：基于SpEL表达式作为判断条件

 @ConditionalOnJava：基于JV版本作为判断条件

 @ConditionalOnJndi：在JNDI存在的条件下差在指定的位置

 @ConditionalOnMissingBean：当容器里没有指定Bean的情况下

 @ConditionalOnMissingClass：当类路径下没有指定类的条件下

 @ConditionalOnNotWebApplication：当前项目不是Web项目的条件下

 @ConditionalOnProperty：指定的属性是否有指定的值

 @ConditionalOnResource：类路径是否有指定的值

 @ConditionalOnSingleCandidate：当指定Bean在容器中只有一个，或者虽然有多个但是指定首选Bean

 @ConditionalOnWebApplication：当前项目是Web项目的条件下。 上面@ConditionalOnXXX都是组合@Conditional元注解，使用了不同的条件Condition
 */
@Configuration
public class ConditionTest {

    //当满足指定条件的时候才加载某个bean的操作
    @Bean(name = "helloservice1")
    @Conditional(WindowsCondition.class)
    public HelloService windowsListService() {
        return new HelloService();
    }

    @Bean(name = "helloservice2")
    @Conditional(LinuxCondition.class)
    public HelloService linuxListService() {
        return new HelloService();
    }

    //只有为true时，才会创建该类的信息
    static class LinuxCondition implements Condition {

        @Override
        public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
            return arg0.getEnvironment().getProperty("os.name").contains("Linux");
        }

    }


    static public class WindowsCondition implements Condition {

        @Override
        public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
            return arg0.getEnvironment().getProperty("os.name").contains("Windows");
        }
    }
}
