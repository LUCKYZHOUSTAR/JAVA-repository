package com.lucky.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:12 2017/12/13
 */
public class SchedulerTest {


    public static void main(String[] args) {
        //通过schedulerFactory获取一个调度器
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            // 通过schedulerFactory获取一个调度器
            scheduler = schedulerfactory.getScheduler();

            //真正执行的任务并不是Job接口的实例，而是用反射的方式实例化的一个JobDetail实例
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail job = JobBuilder.newJob(HelloQuartz.class).withIdentity("JobName", "JobGroupName").build();
            //  corn表达式  每五秒执行一次
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger1", "CronTriggerGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?").withMisfireHandlingInstructionDoNothing()).startNow().build();

            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(job, trigger);


            // 启动调度
            scheduler.start();

            //暂停
//            scheduler.pauseTrigger();
//            scheduler.resumeTrigger(triggerKey);

            Thread.sleep(10000);

            // 停止调度
//            scheduler.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test1() {
        //通过schedulerFactory获取一个调度器
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            // 通过schedulerFactory获取一个调度器
            scheduler = schedulerfactory.getScheduler();


            TriggerKey triggerKey = TriggerKey.triggerKey("JobName", "JobGroupName");
            JobKey jobKey = new JobKey("JobName", "JobGroupName");
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/1 * * * * ?").withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            // JobDetail : jobClass
            Class<? extends Job> jobClass_ = HelloQuartz.class;   // Class.forName(jobInfo.getJobClass());

            JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(jobKey).build();

            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, cronTrigger);


            // 启动调度
            scheduler.start();

            //暂停
//            scheduler.pauseTrigger();
//            scheduler.resumeTrigger(triggerKey);

            Thread.sleep(10000);
            System.out.println("我要开始暂停了");
            scheduler.pauseTrigger(triggerKey);
            Thread.sleep(10000);
            System.out.println("我要开始重新之心管理");
            scheduler.resumeJob(jobKey);
            // 停止调度
//            scheduler.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
