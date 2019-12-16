package com.lucky.jedis.lock;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/16 15:57
 * @Description:分布式可重入锁 内部通过一个threadlocal来维护一个线程的引用计数器，从而可以是实现可重入锁的功能特性
 */
public class RedisWithReentrantLock {

    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();

    private Jedis jedis;


    public RedisWithReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }


    private boolean _lock(String key) {
        return jedis.set(key, "", "nx", "ex", 5L) != null;
    }


    private void _unlock(String key) {
        jedis.del(key);
    }


    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();

        if (Objects.nonNull(refs)) {
            return refs;
        }


        lockers.set(new HashMap<>());


        return lockers.get();

    }


    public boolean lock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);

        //如果不是null的话，代表已经获取到锁了，引用计数器+1即可,就不需要再次获取锁了，代表的是同一个线程
        if (Objects.nonNull(refCnt)) {
            refs.put(key, refCnt + 1);
            return true;
        }

        boolean ok = this._lock(key);

        if (!ok) {
            return false;
        }

        refs.put(key, 1);

        return true;


    }


    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);

        //锁已经被释放掉了，该次释放锁失败
        if (Objects.isNull(refCnt)) {
            return false;
        }

        refCnt -= 1;

        if (refCnt > 0) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this._unlock(key);
        }

        return true;
    }


    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        RedisWithReentrantLock redis = new RedisWithReentrantLock(jedis);
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.unlock("codehole"));
        System.out.println(redis.unlock("codehole"));

    }
}
