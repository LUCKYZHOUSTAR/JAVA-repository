package com.lucky.task.client;

import org.springframework.stereotype.Component;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:24 2017/12/14
 */
@Component
@Task
public class ItemTask implements Executor {
    @Override
    public void execute(TaskContext ctx) {
        System.out.println("开始执行任务了");
    }
}
