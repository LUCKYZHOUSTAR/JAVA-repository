package com.lucky.disruptor.test1;

/**
 * @Author:chaoqiang.zhou
 * @Description:我们从一个简单的例子开始学习Disruptor：生产者传递一个long类型的值给消费者，
 * 而消费者消费这个数据的方式仅仅是把它打印出来。首先声明一个Event来包含需要传递的数据：
 * @Date:Create in 10:58 2017/12/5
 */
public class LongEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
