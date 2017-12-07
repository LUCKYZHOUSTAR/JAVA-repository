package com.lucky.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.CountDownLatch;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:42 2017/12/5
 */
public class InParkingDataEventPublisher implements Runnable {
    Disruptor<InParkingDataEvent> disruptor;
    private CountDownLatch latch;
    //private static int LOOP=10000;//模拟一万车辆入场
    private static int LOOP = 1000000;//模拟10车辆入场


    public InParkingDataEventPublisher(CountDownLatch latch, Disruptor<InParkingDataEvent> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        InParkingDataEventTranslator tradeTransloator = new InParkingDataEventTranslator();
        for (int i = 0; i < LOOP; i++) {

            //内部还是用  this.ringBuffer.publishEvent(eventTranslator);来实现，可以直接用ringbuffer来创建生产者
            disruptor.publishEvent(tradeTransloator);
//            try {
//                Thread.currentThread().sleep(1000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        latch.countDown();
        System.out.println("生产者写完" + LOOP + "个消息");
    }

}

class InParkingDataEventTranslator implements EventTranslator<InParkingDataEvent> {

    /**
     * @param event：空的事件，需要进行包装操作
     * @param sequence：执行事件的序列号
     */
    @Override
    public void translateTo(InParkingDataEvent event, long sequence) {
        this.generateTradeTransaction(event);
    }
//    @Override
//    public void translateTo(InParkingDataEvent event, long sequence) {
//
//        System.out.println("这个方法是干嘛呢");
//    }


    private InParkingDataEvent generateTradeTransaction(InParkingDataEvent event) {
        int num = (int) (Math.random() * 8000);
        num = num + 1000;
        event.setCarLicense("京Z" + num);
        System.out.println("Thread Id " + Thread.currentThread().getId() + " 写完一个event");
        return event;
    }
}