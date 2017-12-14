package com.lucky.task.task;

import com.lucky.task.client.Executor;
import com.lucky.task.client.Task;
import com.lucky.task.client.TaskContext;
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
