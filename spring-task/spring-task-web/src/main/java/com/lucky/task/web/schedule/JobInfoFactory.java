package com.lucky.task.web.schedule;

import com.lucky.task.core.config.ServerOptions;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:内存级别的缓存任务信息，后续用db来替代
 * @Date:Create in 12:36 2017/12/15
 */
public class JobInfoFactory {


    //执行器map信息
    public static Map<String, Set<ServerOptions>> jobs = new ConcurrentHashMap<>();
}
