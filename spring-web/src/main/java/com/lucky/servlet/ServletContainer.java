package com.lucky.servlet;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description: Spring Boot提供了 ServletRegistrationBean，FilterRegistrationBean，ServletListenerRegistrationBean这3个东西来进行配置Servlet、Filter、Listener。
 * @Date:Create in 12:16 2017/12/1
 */
@Configuration
public class ServletContainer {

    @Bean
    public ServletRegistrationBean getDemoServlet() {
        MyServlet demoServlet = new MyServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(demoServlet);
        List<String> urlMappings = new ArrayList<String>();
        urlMappings.add("/hello");////访问，可以添加多个
        registrationBean.setUrlMappings(urlMappings);
        registrationBean.setLoadOnStartup(1);
        //默认为该bean的名称
//        registrationBean.setName(default_name);
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean getDemoFilter() {
        MyFilter demoFilter = new MyFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(demoFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<EventListener> getDemoListener() {
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new MyListener());
        //registrationBean.setOrder(1);
        return registrationBean;
    }
}
