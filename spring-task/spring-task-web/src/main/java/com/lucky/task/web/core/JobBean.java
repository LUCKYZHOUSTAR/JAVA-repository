package com.lucky.task.web.core;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:58 2017/12/14
 */
public class JobBean extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }
}
