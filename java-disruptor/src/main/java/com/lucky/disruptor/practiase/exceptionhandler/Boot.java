package com.lucky.disruptor.practiase.exceptionhandler;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:chaoqiang.zhou
 * @Description:启动信息类信息
 * @Date:Create in 14:45 2017/12/7
 */
public class Boot {


    //必须是2的幂次方，方面ringbuffer取模
    private final static int bufferSize = 1024;

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //就是一个包装类
        Disruptor<Request> disruptor = new Disruptor<Request>(new EventFactory<Request>() {
            @Override
            public Request newInstance() {
                return new Request();
            }
        }, bufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        //来绑定与消费者的关系

        //内部出现异常的话，只会影响一个消费者，不加这句话disruptor.setDefaultExceptionHandler(new ExceptionHandlerTest());就会中断这个消费者

        disruptor.handleEventsWith(new ConsumerHandler(), new ConsumerHandler2());
        //没起到作用
//        disruptor.handleExceptionsWith(new ExceptionHandlerTest());

        //加上这句话，会把异常信息给包裹，sequence++，会继续执行，相当于trycatch，retrydo操作
        disruptor.setDefaultExceptionHandler(new ExceptionHandlerTest());
        //消费者启动
        disruptor.start();


        Producer producer = new Producer(disruptor.getRingBuffer());
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100L);
            producer.publish();
        }
    }


}
