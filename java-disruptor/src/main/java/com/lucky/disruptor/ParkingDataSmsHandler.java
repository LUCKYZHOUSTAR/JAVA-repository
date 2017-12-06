package com.lucky.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:42 2017/12/5
 */
public class ParkingDataSmsHandler implements EventHandler<InParkingDataEvent> {


    @Override
    public void onEvent(InParkingDataEvent event, long sequence, boolean endOfBatch) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLicense = event.getCarLicense();
        System.out.println(String.format("Thread Id %s send %s in plaza sms to user", threadId, carLicense));
    }
}
