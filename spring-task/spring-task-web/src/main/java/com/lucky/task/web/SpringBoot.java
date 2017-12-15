package com.lucky.task.web;

import com.lucky.task.web.core.HandlerService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:05 2017/12/14
 */
@SpringBootApplication
public class SpringBoot {
    public static void main(String[] args) throws SchedulerException {
        ApplicationContext ctx = SpringApplication.run(SpringBoot.class, args);
//        HandlerService handlerService = ctx.getBean(HandlerService.class);
//        handlerService.addJob("ItemTask", "cmc.task.test", "*/1 * * * * ?");
    }


    @Bean(initMethod = "start")
    public Scheduler schedulerBean() throws SchedulerException {
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        return schedulerfactory.getScheduler();
    }
}
