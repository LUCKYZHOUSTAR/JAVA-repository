package com.lucky.disruptor.practiase.producer;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:32 2017/12/7
 */
public class OneToOneBatch {

    private final static int BUFFER_SIZE = 1024;
    // 构造RingBuffer
    private final static RingBuffer<ValueEvent> ringBuffer = RingBuffer.
            createSingleProducer(ValueEvent.EVENT_FACTORY, BUFFER_SIZE, new YieldingWaitStrategy());

    // 构造BatchEventProcessor 及依赖关系
    private final static SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
    private final static ConsumerHandler handler = new ConsumerHandler();
    private final static BatchEventProcessor<ValueEvent> batchEventProcessor = new BatchEventProcessor<ValueEvent>(ringBuffer, sequenceBarrier, handler);

    public static void main(String[] args) {
        // 构造反向依赖
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
    }

}
