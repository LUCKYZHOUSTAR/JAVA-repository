package com.lucky.disruptor.practiase.producer;


import com.lmax.disruptor.EventHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:消费者消费的时候回调事件
 * @Date:Create in 14:37 2017/12/7
 */
public class ConsumerHandler implements EventHandler<ValueEvent> {

    @Override
    public void onEvent(ValueEvent request, long l, boolean b) throws Exception {
        System.out.println("是真的吗" + b);
        System.out.println(Thread.currentThread().getName() + "消费的序号是" + l);
        System.out.println("输出消息的内容信息" + request);
    }
}
