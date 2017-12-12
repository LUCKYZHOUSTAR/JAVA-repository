package com.lucky.boot.configuration;

import com.lucky.boot.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:11 2017/12/12
 */
@Configuration
public class MetricsAutoConfiguration {
    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnMissingBean
    public HelloService helloService() {
        return new HelloService();
    }
}
