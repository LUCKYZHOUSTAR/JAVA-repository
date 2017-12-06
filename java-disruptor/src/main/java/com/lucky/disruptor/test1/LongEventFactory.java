package com.lucky.disruptor.test1;

import com.lmax.disruptor.EventFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:58 2017/12/5
 */
public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
