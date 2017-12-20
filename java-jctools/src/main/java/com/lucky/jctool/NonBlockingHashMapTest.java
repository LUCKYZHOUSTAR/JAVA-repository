package com.lucky.jctool;

import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;

/**
 * @Author:chaoqiang.zhou ConcurrentAutoTable(后面几个map/set结构的基础)
 * NonBlockingHashMap
 * NonBlockingHashMapLong
 * NonBlockingHashSet
 * NonBlockingIdentityHashMap
 * NonBlockingSetInt
 * @Description:内部采用cas的原理，实现了无锁化的竞争操作
 * @Date:Create in 16:48 2017/12/20
 */
public class NonBlockingHashMapTest {

    public static void main(String[] args) {
        Map map = new NonBlockingHashMap();

        map.put("aa", "aa");

    }
}
