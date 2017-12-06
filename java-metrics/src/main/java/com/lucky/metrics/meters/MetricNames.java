package com.lucky.metrics.meters;

import com.codahale.metrics.*;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:07 2017/12/5
 */
public class MetricNames {

    static final MetricRegistry metrics = new MetricRegistry();
    private static final ScheduledReporter scheduledReporter = ConsoleReporter.forRegistry(metrics).build();
    static final Timer processingTimer = metrics.timer("processing");
    ;

    @Test
    public void test1() {
        //创建了一个新的guague

        Gauge guage = metrics.register(MetricRegistry.name(MetricNames.class, "requests", "size"), new Gauge<Object>() {
            @Override
            public Object getValue() {
                return 1;
            }
        });

        System.out.println(guage.getValue());
        System.out.println(guage.getValue());

        System.out.println(guage.getValue());
        System.out.println(guage.getValue());


    }


    @Test
    public void test3() throws Exception {

        for (int i = 0; i < 100; i++) {
            processingTimer.update(
                    System.currentTimeMillis() - System.currentTimeMillis() + 1000, TimeUnit.MILLISECONDS);

        }

        scheduledReporter.start(1000L, TimeUnit.MILLISECONDS);
        Thread.sleep(10000L);
    }

    @Test
    public void test2() {
//        metrics.register(MetricRegistry.name(MetricNames.class, "requests", "size"),
//                new JmxAttributeGauge("net.sf.ehcache:type=Cache,scope=sessions,name=eviction-count", "Value"));
    }
}
