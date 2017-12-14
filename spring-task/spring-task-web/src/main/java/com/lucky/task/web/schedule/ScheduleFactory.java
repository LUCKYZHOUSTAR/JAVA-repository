package com.lucky.task.web.schedule;

import com.lucky.task.web.core.JobBean;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Date;

/**
 * @Author:chaoqiang.zhou
 * @Description:其实就是一个quartz中的一个schedule
 * @Date:Create in 16:50 2017/12/14
 */
@Slf4j
public class ScheduleFactory {

    private static Scheduler scheduler = null;


    public static boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

    public static boolean addJob(String jobName, String jobGroup, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (checkExists(jobName, jobGroup)) {
            log.info(">>>>>>>>> addJob fail, job already exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
            return false;
        }
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
        Class<? extends Job> jobClass_ = JobBean.class;
        JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(jobKey).build();
        Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
        log.info(">>>>>>>>>>> addJob success, jobDetail:{}, cronTrigger:{}, date:{}", jobDetail, cronTrigger, date);
        return true;
    }


    public static boolean removeJob(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            result = scheduler.unscheduleJob(triggerKey);
            log.info(">>>>>>>>>>> removeJob, triggerKey:{}, result [{}]", triggerKey, result);
        }
        return true;
    }

    // Pause
    public static boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {
        // TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
            log.info(">>>>>>>>>>> pauseJob success, triggerKey:{}", triggerKey);
        } else {
            log.info(">>>>>>>>>>> pauseJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }

    // resume
    public static boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {
        // TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.resumeTrigger(triggerKey);
            result = true;
            log.info(">>>>>>>>>>> resumeJob success, triggerKey:{}", triggerKey);
        } else {
            log.info(">>>>>>>>>>> resumeJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }


    // run
    public static boolean triggerJob(String jobName, String jobGroup) throws SchedulerException {
        // TriggerKey : name + group
        JobKey jobKey = new JobKey(jobName, jobGroup);

        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.triggerJob(jobKey);
            result = true;
            log.info(">>>>>>>>>>> runJob success, jobKey:{}", jobKey);
        } else {
            log.info(">>>>>>>>>>> runJob fail, jobKey:{}", jobKey);
        }
        return result;
    }

}
