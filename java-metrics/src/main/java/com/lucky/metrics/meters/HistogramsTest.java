package com.lucky.metrics.meters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:对调用次数进行统计，比meter更加的丰富
 * @Date:Create in 20:48 2017/12/5
 */
public class HistogramsTest {

    static final MetricRegistry metrics = new MetricRegistry();
    //控制台输出组件信息
    static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    @Test
    public void test1() throws Exception {

        reporter.report();
        Histogram histogram = metrics.histogram("timeer");
        for (int i = 0; i < 100; i++) {

            histogram.update(i);

        }

        Thread.sleep(3000L);
        System.out.println(histogram.getSnapshot().get999thPercentile());
        System.out.println(histogram.getSnapshot().getMax());
        System.out.println(histogram.getSnapshot().get999thPercentile());
        System.out.println("最后三十秒");
        Histogram histogram2 = metrics.histogram("timeer2");
        histogram2.update(30);
        Thread.sleep(3000L);
        System.out.println(histogram2.getSnapshot().get999thPercentile());
        System.out.println(histogram2.getSnapshot().getMax());
        System.out.println(histogram2.getSnapshot().get999thPercentile());


    }
}
