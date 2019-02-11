package com.java.common.jersey;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

/**
 * 在主类上加上EnableJersey注解才会生效
 * 通用配置类
 */
@Configuration
@ConditionalOnBean(annotation = EnableJersey.class)
public class WebConfig {

    /**
     * 解决中文内容编码问题，统一用UTF-8编码
     *
     * @return 文件编码的过滤器
     */
    @Bean(name = "utf8Filter")
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
}
