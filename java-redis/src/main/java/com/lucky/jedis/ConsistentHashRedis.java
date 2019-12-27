package com.lucky.jedis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/20 11:12
 * @Description:
 */
public class ConsistentHashRedis {

    // 用跳表模拟一致性hash环,即使在节点很多的情况下,也可以有不错的性能
    private final ConcurrentSkipListMap<Integer, String> circle;

    // 虚拟节点数量
    private final int virtual_size;


    public ConsistentHashRedis(String configs) {
        this.circle = new ConcurrentSkipListMap<>();

        String[] cs = configs.split(",");
        this.virtual_size = getVirtualSize(cs.length);
        for (String c : cs) {
            this.add(c);
        }
    }


    //每一个物理节点，添加几个虚拟的节点，放置到环中
    private void add(String c) {
        if (c == null) {
            return;
        }

        for (int i = 0; i < virtual_size; ++i) {
            String virtual = c + "-N" + i;
            int hash = getHash(virtual);
            circle.put(hash, virtual);
        }
    }


    private int getVirtualSize(int length) {
        return 150;
    }


    //对外提供的set方法
    public void set(String key, String v) {
        getJedisFromCircle(key).set(key, v);


    }


    public String get(String k) {
        return getJedisFromCircle(k).get(k);

    }


    private Jedis getJedisFromCircle(String key) {
        int keyHash = getHash(key);
        ConcurrentNavigableMap<Integer, String> tailMap = circle.tailMap(keyHash);

        String config = tailMap.isEmpty() ? circle.firstEntry().getValue() : tailMap.firstEntry().getValue();
        //注意由于使用了虚拟节点，这里需要建立虚拟节点---》真实节点之间的映射
        String[] cs = config.split("-");
        return new Jedis(cs[0]);

    }

    /**
     * 对外暴露的添加节点接口
     */
    public boolean addJedis(String cs) {
        add(cs);
        return true;
    }

    /**
     * 对外暴露的删除节点节点
     */
    public boolean deleteJedis(String cs) {
        delete(cs);
        return true;
    }

    //获取hashcode
    private int getHash(String s) {
        return Math.abs(s.hashCode());
    }


    //移除某个节点
    private void delete(String cs) {
        if (cs == null) {
            return;
        }

        for (int i = 0; i < virtual_size; ++i) {
            String virttual = cs + "-N" + i;
            int hash = getHash(virttual);
            circle.remove(hash, virttual);
        }
    }
}
