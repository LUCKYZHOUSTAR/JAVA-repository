package com.lucky.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:chaoqiang.zhou
 * @Description:红包活动，例如抢2000万的红包，如何控制并发 对于红包活动来说，就是放置一个key作为总金额来控制，用给一个key来做并发操作，如果并发大的话，可以把key进行拆分，多放置几个key进行操作
 * 挂掉就挂掉了，活动进行就几分钟，一切措施都补救不了
 * @Date:Create in 10:58 2017/12/4
 */
public class bonus {
    private static final String BONUS_KEY = "bonus";

    public static final String ip = "192.168.54.101";
    public static final int port = 6379;
    public static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 2; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    plus(BONUS_KEY, 50);
                    countDownLatch.countDown();
                }
            });
        }
//
//
        countDownLatch.await();
        Jedis jedis = RedisUtil.getJedis(ip, port);
        String ss = jedis.get("bonus");
        jedis.del("bonus");
        System.out.println(ss);
    }

    public static boolean plusBonus(String key, long value) {
        if (plus(key, value) > 10000) {
            return false;
        }
        return true;
    }

    public static long plus(String key, long value) {

        Objects.requireNonNull(key);

        Jedis jedis = null;
        JedisPool pool = null;
        try {
            jedis = RedisUtil.getJedis(ip, port);
            return jedis.incrBy(key, value);
        } finally {
            RedisUtil.getPool(ip, port).returnResource(jedis);
        }

    }
}
