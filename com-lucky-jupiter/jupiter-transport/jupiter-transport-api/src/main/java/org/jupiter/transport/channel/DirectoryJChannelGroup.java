package org.jupiter.transport.channel;

import org.jupiter.common.util.Maps;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:21 2018/1/18
 */
public class DirectoryJChannelGroup {

    // key: 服务标识; value: 提供服务的节点列表(group list)
    private final ConcurrentMap<String, CopyOnWriteGroupList> groups = Maps.newConcurrentMap();
    // 对应服务节点(group)的引用计数
    private final GroupRefCounterMap groupRefCounter = new GroupRefCounterMap();






    static class GroupRefCounterMap extends ConcurrentHashMap<JChannelGroup, AtomicInteger> {

        private static final long serialVersionUID = 6590976614405397299L;

        public AtomicInteger getOrCreate(JChannelGroup key) {
            AtomicInteger counter = super.get(key);
            if (counter == null) {
                AtomicInteger newCounter = new AtomicInteger(0);
                counter = super.putIfAbsent(key, newCounter);
                if (counter == null) {
                    counter = newCounter;
                }
            }
            return counter;
        }
    }
}
