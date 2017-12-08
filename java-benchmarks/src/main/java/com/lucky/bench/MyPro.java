package com.lucky.bench;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:25 2017/12/8
 */
public class MyPro {
    public static void main(String [] args) throws Exception{
        Options opt = new OptionsBuilder()
                .include(JmhDemo.class.getSimpleName())
                .forks(1)
                .warmupIterations(5) //预热次数
                .measurementIterations(5) //真正执行次数
                .build();

        new Runner(opt).run();
    }
}
