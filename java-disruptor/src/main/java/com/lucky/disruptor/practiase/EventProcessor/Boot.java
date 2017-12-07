package com.lucky.disruptor.practiase.EventProcessor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:chaoqiang.zhou
 * @Description:启动信息类信息
 * http://blog.csdn.net/zhxdick/article/details/52148806
 * @Date:Create in 14:45 2017/12/7
 */
public class Boot {


    //必须是2的幂次方，方面ringbuffer取模
    private final static int bufferSize = 1024;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //就是一个包装类
        //就是一个包装类
        Disruptor<Request> disruptor = new Disruptor<Request>(new EventFactory<Request>() {
            @Override
            public Request newInstance() {
                return new Request();
            }
        }, bufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());        //来绑定与消费者的关系

        //这样做与默认的做法是一样的
        Processor processor = new Processor(disruptor.getRingBuffer(), new com.lucky.disruptor.practiase.EventProcessor.ConsumerHandler());
        disruptor.handleEventsWith(processor.getProcessor());


        /**
         * executor是java.util.concurrent.Executor类型的对象，
         * 根据需要可用采用JDK提供的ThreadPoolExecutor,也可以自己实现；
         * 但从上面代码也可以看出，通过executor.execute方式启动eventprocessor,
         * 也就是disruptor通常会将每个handler放在独立的线程中进行处理；但这有个问题，
         * 比如http请求，如果使用一个handler进行实现，那意味着所有http请求将由一个线程进行处理，
         * 那岂不是性能反而下降了？那disruptor的优势体现在哪呢？
         executor.execute(eventprocessor);
         */
        //消费者启动
        disruptor.start();


        Producer producer = new Producer(disruptor.getRingBuffer());
        for (int i = 0; i < 1000; i++) {
            producer.publish();
        }
    }


}
