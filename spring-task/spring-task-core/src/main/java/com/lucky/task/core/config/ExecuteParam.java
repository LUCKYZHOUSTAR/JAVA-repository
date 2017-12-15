package com.lucky.task.core.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:执行参数信息
 * @Date:Create in 14:47 2017/12/13
 */
@Getter
@Setter
public class ExecuteParam implements Serializable {


    private ExecuteType type;
    /**
     * 任务唯一标识
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;


    private String address;

    /**
     * 参数
     */
    private List<Arg> args;


    /**
     * 执行方式，手动或者自动
     */
    public enum ExecuteType {
        AUTO(0), MANUAL(1);

        private int value;

        ExecuteType(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
