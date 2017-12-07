package com.lucky.disruptor.practiase.producer;

import com.lmax.disruptor.EventFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:34 2017/12/7
 */
public final class ValueEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(final long value) {
        this.value = value;
    }

    public static final EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {
        @Override
        public ValueEvent newInstance() {
            return new ValueEvent();
        }
    };
}