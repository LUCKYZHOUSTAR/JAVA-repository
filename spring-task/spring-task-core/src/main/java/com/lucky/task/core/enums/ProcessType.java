package com.lucky.task.core.enums;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:34 2017/12/14
 */
public enum ProcessType {


    request("处理task请求", 1),
    report("task信息上报", 2);
    private int value;
    private String name;

    ProcessType(String name, int value) {
        this.name = name;
        this.value = value;

    }


    public int value(){
        return this.value;
    }
}
