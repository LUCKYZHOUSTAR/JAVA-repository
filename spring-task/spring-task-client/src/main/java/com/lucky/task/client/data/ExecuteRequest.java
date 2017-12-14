package com.lucky.task.client.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:执行任务参数信息
 * @Date:Create in 14:49 2017/12/13
 */

@Getter
@Setter
public class ExecuteRequest {

    public String Name;

    public List<Arg> Args;

    public LocalDateTime Time;

    public static class Arg {
        public String Name;
        public String Value;
    }
}
