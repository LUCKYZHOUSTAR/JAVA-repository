package com.lucky.disruptor.test2;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

/**
 * @Author:chaoqiang.zhou
 * @Description:消费者除了实现EventHandler接口之外，还实现了WorkHandler接口，为啥了，因为后面我们要使用了WokerPool来发送该实体类，所以这里就让该实体类实现两个接口
 * @Date:Create in 12:33 2017/12/7
 */
public class TradeHandler implements EventHandler<Trade>, WorkHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(Trade event) throws Exception {
        event.setId(UUID.randomUUID().toString());
        System.out.println(event.getId());
    }
}
