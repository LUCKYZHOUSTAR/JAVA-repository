package com.lucky.bench;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:24 2017/12/8
 */
public class JmhDemo {
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void stringLoop(){
        String str = new String();
        for(int i = 0; i<100; i++){
            str += "str\t";
            str += "str\t";
            str += "str\t";
            str += "str\t";
            str += "str\t";
        }


    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void builderLoop(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<100; i++){
            builder.append("str\t");
            builder.append("str\t");
            builder.append("str\t");
            builder.append("str\t");
            builder.append("str\t");
        }


    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void bufferLoop(){
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < 100; i++){
            buffer.append("str\t");
            buffer.append("str\t");
            buffer.append("str\t");
            buffer.append("str\t");
            buffer.append("str\t");
        }
    }
}
