package com.lucky.disruptor;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:41 2017/12/5
 */
public class InParkingDataEvent {
    private String carLicense = "";

    public void setCarLicense(String carLicense)
    {
        this.carLicense = carLicense;
    }
    public String getCarLicense()
    {
        return carLicense;
    }
}
