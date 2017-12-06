package com.lucky.metrics.meters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:用来记录一次请求的时间
 * @Date:Create in 20:40 2017/12/5
 */
public class TimerTest {

    static final MetricRegistry metrics = new MetricRegistry();
    //控制台输出组件信息
    static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    @Test
    public void test1() throws Exception {

        reporter.report();
        Timer timer = metrics.timer("timeer");
        for (int i = 0; i < 100; i++) {
            timer.update(
                    System.currentTimeMillis() - System.currentTimeMillis() - i, TimeUnit.MILLISECONDS);
            System.out.println(timer.getFifteenMinuteRate());
            System.out.println(timer.getMeanRate());
            System.out.println(timer.getOneMinuteRate());
            System.out.println(timer.getSnapshot().get999thPercentile());
        }

        Thread.sleep(3000L);

    }


}
