package com.lucky.metrics;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;

import javax.print.attribute.standard.RequestingUserName;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:30 2017/12/5
 */
public class MetricsHolder {
    // 请求处理耗时统计(从request被解码开始, 到response数据被刷到OS内核缓冲区为止)
    static final Timer processingTimer              = Metrics.timer("processing");
    // 请求被拒绝次数统计
    static final Meter rejectionMeter               = Metrics.meter("rejection");
    // 请求数据大小统计(不包括Jupiter协议头的16个字节)
    static final Histogram requestSizeHistogram     = Metrics.histogram("request.size");
    // 响应数据大小统计(不包括Jupiter协议头的16个字节)
    static final Histogram responseSizeHistogram    = Metrics.histogram("response.size");

    public static void main(String[] args) {
    }
}
