package com.lucky.metric.web;

import com.codahale.metrics.ConsoleReporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:30 2017/12/20
 */
@SpringBootApplication
public class Boot {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Boot.class, args);

        // 启动Reporter
        ConsoleReporter reporter = ctx.getBean(ConsoleReporter.class);
        reporter.start(1, TimeUnit.SECONDS);

    }

}
