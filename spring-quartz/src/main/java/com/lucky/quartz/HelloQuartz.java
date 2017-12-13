package com.lucky.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author:chaoqiang.zhou
 * @Description:因为是反射所以不能是内部类信息
 * @Date:Create in 10:58 2017/12/13
 */
public class HelloQuartz implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //context 是任务执行的上下文，可以拿到任务的Jobkey（保存任务的name，group等信息）
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("任务key：" + jobName + "  Hello Quartz !");
    }
}
