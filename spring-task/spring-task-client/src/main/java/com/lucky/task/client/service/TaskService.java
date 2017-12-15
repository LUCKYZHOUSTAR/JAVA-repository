package com.lucky.task.client.service;

import com.lucky.task.core.config.ExecuteParam;
import com.lucky.task.core.net.codec.Response;

/**
 * @Author:chaoqiang.zhou
 * @Description:计划任务执行服务
 * @Date:Create in 14:56 2017/12/13
 */

public interface TaskService {
    Response execute(ExecuteParam param);
}