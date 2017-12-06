package com.lucky.disruptor.test1;


import com.lmax.disruptor.EventHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:我们还需要一个事件消费者，也就是一个事件处理器。这个事件处理器简单地把事件中存储的数据打印到终端：
 * @Date:Create in 10:59 2017/12/5
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(longEvent.getValue());
    }

}
