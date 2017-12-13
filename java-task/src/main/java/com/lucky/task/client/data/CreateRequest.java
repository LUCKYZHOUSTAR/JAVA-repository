package com.lucky.task.client.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.Trigger;

import java.util.List;

/**
 * 创建业务任务信息参数信息
 */
@Getter
@Setter
public class CreateRequest {

    /**
     * 任务名称
     */
    public String Name;
    /**
     * 任务描述
     */
    public String Note;

    /**
     * 执行器服务名称
     */
    public String Executer;
    /**
     * 触发器列表
     */
    public List<Trigger> Triggers;
    /**
     * 参数列表
     */
    public List<Arg> Args;
}
