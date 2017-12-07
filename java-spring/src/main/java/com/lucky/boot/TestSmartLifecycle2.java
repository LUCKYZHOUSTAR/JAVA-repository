package com.lucky.boot;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:46 2017/12/7
 */
@Component
public class TestSmartLifecycle2 implements SmartLifecycle {
    private boolean isRunning = false;

    @Override
    public void start() {
        System.out.println("test start-------------------------");
        isRunning = true;
    }

    @Override
    public void stop() {
        System.out.println("test stop-------------------------");
        isRunning = false;

    }

    @Override
    public boolean isRunning() {
        System.out.println("test isRunning-------------------------");
        return isRunning;
    }

    @Override
    public int getPhase() {
        System.out.println("test getPhase-------------------------");
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        System.out.println("test isAutoStartup-------------------------");
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        System.out.println("test callback-------------------------");
        isRunning = false;
    }

}
