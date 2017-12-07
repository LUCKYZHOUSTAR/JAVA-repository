package com.lucky.disruptor.practiase.exceptionhandler;

import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description:生产者信息
 * @Date:Create in 14:39 2017/12/7
 */
public class Producer {


    private static final AtomicLong index = new AtomicLong();
    private final RingBuffer<Request> ringBuffer;

    public Producer(RingBuffer<Request> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    /**
     * 用最原始的方法来发布消息
     */
    public void publish() {

        //获取下一个槽的序号
        long sequence = ringBuffer.next();
        try {
            //开始赋值内容消息，这样每次都不要创建新的对象，减少了内存的开销
            Request request1 = ringBuffer.get(sequence);
            request1.setBody("啦啦");
            request1.setId(index.getAndIncrement());
        } finally {
            //发布消息
            ringBuffer.publish(sequence);
        }
    }
}
