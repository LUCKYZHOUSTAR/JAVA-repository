package com.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:http://blog.csdn.net/guozebo/article/details/51590517
 * @Date:Create in 12:16 2017/11/17
 */
public class GuavaCacheTest {


    /**
     * 请一定要记住GuavaCache的实现代码中没有启动任何线程！！Cache中的所有维护操作，包括清除缓存、写入缓存等，都是通过调用线程来操作的。这在需要低延迟服务场景中使用时尤其需要关注，可能会在某个调用的响应时间突然变大。
     * GuavaCache毕竟是一款面向本地缓存的，轻量级的Cache，适合缓存少量数据。如果你想缓存上千万数据，可以为每个key设置不同的存活时间，并且高性能，那并不适合使用GuavaCache。
     */
    final static Cache<Integer, String> cache = CacheBuilder.newBuilder()
            //设置cache的初始大小为10，要合理设置该值
            .initialCapacity(10)
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
            .concurrencyLevel(5)
            //设置Cache的最大容量数
            .maximumSize(10000)
            //设置cache中的数据在写入之后的存活时间为10秒
            .expireAfterWrite(10, TimeUnit.SECONDS)
            //构建cache实例
            .build();


    @Test
    public void getTest() {


        cache.put(1, "Hi");

        for (int i = 0; i < 100; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            System.out.println(sdf.format(new Date())
                    + "  key:1 ,value:" + cache.getIfPresent(1));
        }
    }
}
