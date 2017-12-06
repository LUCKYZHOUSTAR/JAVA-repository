package com.lucky.metrics.meters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.sun.management.jmx.MBeanServerImpl;
import org.junit.Test;

import javax.management.MBeanServer;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:25 2017/12/6
 */
public class GCTest {


    static final MetricRegistry metrics = new MetricRegistry();
    //控制台输出组件信息
    static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    @Test
    public void test1() throws Exception {
        MBeanServer mBeanServer=new MBeanServerImpl();
        metrics.register("jvm.mem", new MemoryUsageGaugeSet());
        metrics.register("jvm.gc", new GarbageCollectorMetricSet());
        metrics.register("jvm.buffer", new BufferPoolMetricSet(mBeanServer));

//        reporter.report();
        reporter.start(5, TimeUnit.SECONDS);
        Thread.sleep(10000L);
    }



}
