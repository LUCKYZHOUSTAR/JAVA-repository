package com.lucky.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:数据入库
 * @Date:Create in 10:41 2017/12/5
 */
public class ParkingDataInDbHandler implements EventHandler<InParkingDataEvent>,WorkHandler<InParkingDataEvent> {

    @Override
    public void onEvent(InParkingDataEvent event) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLicense = event.getCarLicense();
        System.out.println(String.format("Thread Id %s save %s into db ....",threadId,carLicense));
    }

    @Override
    public void onEvent(InParkingDataEvent event, long sequence, boolean endOfBatch) throws Exception {
        // TODO Auto-generated method stub
        this.onEvent(event);
    }

}
