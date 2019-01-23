package com.lucky.design.patterns.databus;

import java.util.function.Consumer;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/1/15 11:36
 * @Description:Members receive events from the Data-Bus.
 */
public interface Member extends Consumer<DataType> {

    void accept(DataType dataType);
}
