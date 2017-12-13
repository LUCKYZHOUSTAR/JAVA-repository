package com.lucky.task.client.service;

import com.lucky.task.client.data.ExecuteParam;
import com.lucky.task.client.data.Result;

/**
 * @Author:chaoqiang.zhou
 * @Description:计划任务执行服务
 * @Date:Create in 14:56 2017/12/13
 */

public interface TaskService {
    Result execute(ExecuteParam param);
}