package com.lucky.task.client;

import com.lucky.task.client.data.Arg;
import com.lucky.task.client.data.ExecuteParam;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 执行上下文的信息
 */
@Getter
public class TaskContext {
    private String name;
    private String id;
    private Map args = new HashMap();

    public TaskContext(ExecuteParam param) {
        this.name = param.getName();
        this.id = param.getId();
        List<Arg> list = param.getArgs();
        if (list != null && !list.isEmpty()) {
            list.forEach(arg -> this.args.put(arg.Name, arg.Value));
        }
    }
}
