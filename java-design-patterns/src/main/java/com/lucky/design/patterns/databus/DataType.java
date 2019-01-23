package com.lucky.design.patterns.databus;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/1/15 11:34
 * @Description:Events are sent via the Data-Bus.
 */
public interface DataType {

    DataBus getDataBus();

    void setDataBus();
}
