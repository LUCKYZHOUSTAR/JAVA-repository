package com.lucky.metrics.meters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:主要是用来记录频率，看每次一个调用就加1
 * @Date:Create in 20:29 2017/12/5
 */
public class MeterTest {

    static final MetricRegistry metrics = new MetricRegistry();
    //控制台输出组件信息
    static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    @Test
    public void Test1() throws Exception {
        //通过控制台进行输出

        reporter.start(1, TimeUnit.SECONDS);
        Meter meter = metrics.meter("request");
        for (int i = 0; i < 100; i++) {
            //代表访问一次
            meter.mark();
        }

        System.out.println(meter.getCount());
        System.out.println(meter.getFifteenMinuteRate());
        System.out.println(meter.getMeanRate());
        System.out.println(meter.getOneMinuteRate());

        Thread.sleep(3000L);
    }
}
