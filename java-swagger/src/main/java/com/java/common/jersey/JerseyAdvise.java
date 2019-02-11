/*
                            _ooOoo_  
                           o8888888o  
                           88" . "88  
                           (| -_- |)  
                            O\ = /O  
                        ____/`---'\____  
                      .   ' \\| |// `.  
                       / \\||| : |||// \  
                     / _||||| -:- |||||- \  
                       | | \\\ - /// | |  
                     | \_| ''\---/'' | |  
                      \ .-\__ `-` ___/-. /  
                   ___`. .' /--.--\ `. . __  
                ."" '< `.___\_<|>_/___.' >'"".  
               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
                 \ \ `-. \_ __\ /__ _/ .-` / /  
         ======`-.____`-.___\_____/___.-`____.-'======  
                            `=---='  
  
         .............................................  
                  佛祖镇楼                  BUG辟易  
          佛曰:  
                  写字楼里写字间，写字间里程序员；  
                  程序人员写程序，又拿程序换酒钱。  
                  酒醒只在网上坐，酒醉还来网下眠；  
                  酒醉酒醒日复日，网上网下年复年。  
                  但愿老死电脑间，不愿鞠躬老板前；  
                  奔驰宝马贵者趣，公交自行程序员。  
                  别人笑我忒疯癫，我笑自己命太贱；  
                  不见满街漂亮妹，哪个归得程序员？
*/
package com.java.common.jersey;

import com.google.common.collect.Lists;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import java.util.List;
import java.util.Map;

/**
 * jersey的增强。自动添加 filter和resource
 * 扫描 Component和Path的注解
 * 并且注册到 jersey的发现器上面
 * 在主类上加上EnableJersey注解才会生效
 */
@Order(0)
@Component
@ConditionalOnBean(annotation = EnableJersey.class)
public class JerseyAdvise implements CommandLineRunner {
    /**
     * slf4j日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * spring的上下文
     */
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * jersey的资源配置，执行register
     */
    @Autowired
    private ResourceConfig resourceConfig;

    @Override
    public void run(String... args) {
        /*设置bean的校验的参数*/
        resourceConfig.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        resourceConfig.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        /*FormDataParam的集成*/
        resourceConfig.register(MultiPartFeature.class);
        /*swagger.json的集成*/
        resourceConfig.register(SwaggerSerializers.class);
        /*注册jersey的Resource的过滤器**/
        long start = System.currentTimeMillis();
        logger.info("init the jersey resource start");
        logger.info("get all the Component annation beans");
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Component.class);
        List<Class<?>> classes = Lists.newArrayList();
        beans.values().stream()
                .filter(k -> !AopUtils.isJdkDynamicProxy(k))
                .map(this::getClassOfBean)
                .filter(v -> v.getAnnotation(Path.class) != null)
                .forEach(classes::add);
        logger.info("the register jersey resource size is {}", classes.size());
        classes.forEach(clazz -> {
            logger.info("register the jersey resource is {}", clazz.getName());
            resourceConfig.register(clazz);
        });
        logger.info("init the jersey resource success,use {}ms", System.currentTimeMillis() - start);
        /*注册jersey的Request的过滤器**/
        logger.info("init the ContainerRequestFilter start");
        Map<String, ContainerRequestFilter> mapRequest = applicationContext.getBeansOfType(ContainerRequestFilter.class);
        logger.info("the register jersey ContainerRequestFilter size is {}", mapRequest.size());
        mapRequest.values().forEach(v -> {
            Class<?> clazz = getClassOfBean(v);
            logger.info("regitster the ContainerRequestFilter is {}", clazz);
            resourceConfig.register(clazz);
        });
        logger.info("init the ContainerRequestFilter success");
        /*注册jersey的Response的过滤器**/
        logger.info("init the ContainerResponseFilter start");
        Map<String, ContainerResponseFilter> mapResponse = applicationContext.getBeansOfType(ContainerResponseFilter.class);
        logger.info("the register jersey ContainerResponseFilter size is {}", mapResponse.size());
        mapResponse.values().forEach(v -> {
            Class<?> clazz = getClassOfBean(v);
            logger.info("regitster the ContainerResponseFilter is {}", clazz);
            resourceConfig.register(clazz);

        });
        logger.info("init the ContainerResponseFilter success");
        /*注册异常处理**/
        logger.info("init the ExceptionMapper start");
        Map<String, ExceptionMapper> exceptionMapperMap = applicationContext.getBeansOfType(ExceptionMapper.class);
        logger.info("the register jersey ExceptionMapper size is {}", exceptionMapperMap.size());
        exceptionMapperMap.values().forEach(v -> {
            Class<?> clazz = getClassOfBean(v);
            logger.info("regitster the ExceptionMapper is {}", clazz);
            resourceConfig.register(clazz, -1);
        });
        logger.info("init the ExceptionMapper success");
        /*注册ReaderInterceptor**/
        logger.info("init the ReaderInterceptor start");
        Map<String, ReaderInterceptor> readerInterceptorMap = applicationContext.getBeansOfType(ReaderInterceptor.class);
        logger.info("the register jersey ReaderInterceptor size is {}", readerInterceptorMap.size());
        readerInterceptorMap.values().forEach(v -> {
            Class<?> clazz = getClassOfBean(v);
            logger.info("regitster the ReaderInterceptor is {}", clazz);
            resourceConfig.register(clazz);
        });
        logger.info("init the ReaderInterceptor success");
        /*注册WriterInterceptor**/
        logger.info("init the WriterInterceptor start");
        Map<String, WriterInterceptor> writerInterceptorMap = applicationContext.getBeansOfType(WriterInterceptor.class);
        logger.info("the register jersey WriterInterceptor size is {}", writerInterceptorMap.size());
        writerInterceptorMap.values().forEach(v -> {
            Class<?> clazz = getClassOfBean(v);
            logger.info("regitster the WriterInterceptor is {}", clazz);
            resourceConfig.register(clazz);
        });
        logger.info("init the WriterInterceptor success");
        /*初始化参数校验*/
        logger.info("init the contextResolver start");
        Map<String, ContextResolver> contextResolverMap = applicationContext.getBeansOfType(ContextResolver.class);
        logger.info("the register jersey contextResolver size is {}", writerInterceptorMap.size());
        contextResolverMap.values().forEach(v -> {
            Class<?> clazz = getClassOfBean(v);
            logger.info("regitster the contextResolver is {}", clazz);
            resourceConfig.register(clazz);
        });
        logger.info("init the contextResolver success");
    }

    /**
     * 根据注入的实体获取最原始的 Class
     *
     * @param bean 源Bean
     * @return bean的带有@Component注解的class
     */
    private Class<?> getClassOfBean(Object bean) {
        Class<?> clazz = bean.getClass();
        /*循环直到非Object的对象*/
        while (!clazz.equals(Object.class)) {
            if (clazz.getAnnotation(Component.class) != null) {
                return clazz;
            } else {
                /*设置superClass*/
                clazz = clazz.getSuperclass();
            }
        }
        return bean.getClass();
    }
}
