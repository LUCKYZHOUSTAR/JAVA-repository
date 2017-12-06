package com.lucky.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:03 2017/12/5
 */
public class test {
    //标准的核心是metricregistry类，它是所有应用程序的度量的容器。继续创建一个新的：
    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String args[]) throws  Exception{
        startReport();
        Meter requests = metrics.meter("requests");
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000L);
            //代表请求
            requests.mark();
            requests.getFifteenMinuteRate();
        }
        wait5Seconds();
    }

    static void startReport() {
        //通过控制台进行输出
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait5Seconds() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
        }
    }
}
