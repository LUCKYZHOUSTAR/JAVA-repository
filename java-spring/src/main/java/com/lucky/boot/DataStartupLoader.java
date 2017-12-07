package com.lucky.boot;

import org.springframework.context.SmartLifecycle;

/**
 * @Author:chaoqiang.zhou
 * @Description:自动加载数据
 * @Date:Create in 10:26 2017/12/7
 */
public abstract class DataStartupLoader implements SmartLifecycle {

    private volatile boolean running = false;

    private final Object monitor = new Object();

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {

        this.stop();
        runnable.run();
    }

    @Override
    public void start() {

        synchronized (monitor) {
            if (!this.running) {
                doLoad();
            }
            this.running = true;
        }


    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    protected abstract void doLoad();
}
