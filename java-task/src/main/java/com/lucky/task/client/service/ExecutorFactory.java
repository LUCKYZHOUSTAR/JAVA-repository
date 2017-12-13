package com.lucky.task.client.service;

import com.lucky.task.client.Executor;

import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:执行器工厂类
 * @Date:Create in 15:57 2017/12/13
 */
public class ExecutorFactory {

    private static Map<String, Executor> executors;


    public static void register(String name, Executor executor) {
        executors.putIfAbsent(name, executor);
    }

    private ExecutorFactory() {
    }

    private static class SingletonHolder {
        //静态初始化器，由JVM来保证线程安全
        private static TaskService instance = new TaskServiceImpl(executors);
    }


    public static TaskService getService(String name) {
        return SingletonHolder.instance;
    }


}
