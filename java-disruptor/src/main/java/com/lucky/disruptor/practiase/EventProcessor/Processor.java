package com.lucky.disruptor.practiase.EventProcessor;

import com.lmax.disruptor.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:35 2017/12/7
 */
public class Processor {

    private RingBuffer<Request> ringBuffer;
    private SequenceBarrier sequenceBarrier;
    private EventHandler<Request> eventHandler;

    public Processor(RingBuffer<Request> ringBuffer, EventHandler<Request> eventHandler) {
        this.ringBuffer = ringBuffer;
        //类似于消费者的指针操作
        /**
         * 可用看到，每个EventProcessor(默认BatchEventProcessor)都有自己的Sequence，也就是说队列中的每个消息，每个EventProcessor都要消费一次；
         * 那么当有多个Handler时，Handler的处理顺序是怎么样的？这个问题留待后面再回答；
         */
        this.sequenceBarrier = this.ringBuffer.newBarrier();
        this.eventHandler = eventHandler;
    }

    public EventProcessor getProcessor() {

        /**
         * BatchEvenProcessor
         在Disruptor中，消费者是以EventProcessor的形式存在的。其中一类消费者是BatchEvenProcessor。每个BatchEvenProcessor有一个Sequence，
         来记录自己消费RingBuffer中消息的情况。所以，一个消息必然会被每一个BatchEvenProcessor消费。
         */
        EventProcessor eventProcessor = new BatchEventProcessor<>(this.ringBuffer, sequenceBarrier, eventHandler);
        return eventProcessor;
    }

//
//    public EventProcessor getWorkProcessor() {
//        /**
//         *
//         *另一类消费者是WorkProcessor。每个WorkProcessor也有一个Sequence，
//         * 多个WorkProcessor还共享一个Sequence用于互斥的访问RingBuffer。
//         * 一个消息被一个WorkProcessor消费，
//         * 就不会被共享一个Sequence的其他WorkProcessor消费。
//         * 这个被WorkProcessor共享的Sequence相当于尾指针。
//         */
//        EventProcessor eventProcessor = new WorkProcessor<>(this.ringBuffer, sequenceBarrier, eventHandler,new ExceptionHandlerTest());
//    }
}
