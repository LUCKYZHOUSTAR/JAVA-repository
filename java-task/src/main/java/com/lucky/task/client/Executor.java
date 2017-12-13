package com.lucky.task.client;


/**
 * 执行器的接口信息
 */
@FunctionalInterface
public interface Executor {
    void execute(TaskContext ctx);
}
