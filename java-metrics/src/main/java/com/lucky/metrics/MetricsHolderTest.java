package com.lucky.metrics;

import com.codahale.metrics.ScheduledReporter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:31 2017/12/5
 */
public class MetricsHolderTest {


    @Test
    public void timerTest() throws Exception{
        System.out.println(System.getProperty("user.dir"));
        for (int i = 0; i < 100; i++) {
            MetricsHolder.processingTimer.update(
                    System.currentTimeMillis() - System.currentTimeMillis() + 1000, TimeUnit.MILLISECONDS);

        }

        Thread.sleep(10000L);
    }
}
