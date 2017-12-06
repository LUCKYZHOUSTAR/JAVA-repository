package com.lucky.metrics.meters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 20:34 2017/12/5
 */
public class CounterTest {
    static final MetricRegistry metrics = new MetricRegistry();
    //控制台输出组件信息
    static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    @Test
    public void Test1(){
        Counter counter=metrics.counter("aa");

        counter.inc();
        counter.inc();
        System.out.println(counter.getCount());
    }
}
