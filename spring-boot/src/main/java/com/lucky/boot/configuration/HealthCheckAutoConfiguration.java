package com.lucky.boot.configuration;

import com.lucky.boot.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:chaoqiang.zhou
 * @Description:只会扫描到该包，但是不会当成bean进行注入
 * @Date:Create in 15:12 2017/12/12
 */
@Configuration
public class HealthCheckAutoConfiguration {
    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnMissingBean
    public HelloService helloService() {
        return new HelloService();
    }
}
