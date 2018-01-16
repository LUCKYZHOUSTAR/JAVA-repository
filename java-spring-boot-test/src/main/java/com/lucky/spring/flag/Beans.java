package com.lucky.spring.flag;

import com.lucky.spring.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:17 2017/12/12
 */


//类似与配置文件中的beans，下面的bean就是beans下面的bean操作，
@Configuration
public class Beans {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public HelloService helloService() {
        return new HelloService();
    }


}
