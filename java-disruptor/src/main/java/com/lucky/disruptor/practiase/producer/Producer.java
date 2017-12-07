package com.lucky.disruptor.practiase.producer;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:28 2017/12/7
 */
public class Producer {
    private final int buffersize = 1024;
    /**
     * 单生产者应用场景
     */
    private final RingBuffer<Request> ringBuffer = RingBuffer.
            createSingleProducer(new EventFactory<Request>() {
                @Override
                public Request newInstance() {
                    return new Request();
                }
            }, buffersize, new YieldingWaitStrategy());


    /**
     * 多生产者
     *
     * @param args
     */

    private final RingBuffer<Request> ringBuffer2 = RingBuffer.
            createMultiProducer(new EventFactory<Request>() {
                @Override
                public Request newInstance() {
                    return new Request();
                }
            }, buffersize, new BusySpinWaitStrategy());


    /**
     * 添加消息
     * // 阶段1：申请节点，并将消息放入节点中
     * long next = rb.next();
     * rb.get(next).setValue(0);
     * <p>
     * // 阶段2：提交节点
     * rb.publish(next);
     */





    public static void main(String[] args) {

    }
}
