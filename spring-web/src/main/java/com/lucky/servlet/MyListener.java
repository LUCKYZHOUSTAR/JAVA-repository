package com.lucky.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:24 2017/12/1
 */
public class MyListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("listener contextInitialized...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed...");
    }
}
