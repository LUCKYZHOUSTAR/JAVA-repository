package org.jupiter.transport;

import org.jupiter.common.util.Maps;
import org.jupiter.common.util.internal.logging.InternalLogger;
import org.jupiter.common.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:10 2018/1/18
 */
public class JConnectionManager {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(JConnectionManager.class);

    private final ConcurrentMap<UnresolvedAddress, CopyOnWriteArrayList<JConnection>> connections = Maps.newConcurrentMap();

    public void manage(JConnection connection) {
        UnresolvedAddress address = connection.getAddress();
        CopyOnWriteArrayList<JConnection> list = connections.get(address);
        if (list == null) {
            CopyOnWriteArrayList<JConnection> newList = new CopyOnWriteArrayList<>();
            list = connections.put(address, newList);
            if (list == null) {
                list = newList;
            }
        }

        list.add(connection);
    }


    public void cancelReconnect(UnresolvedAddress address) {
        CopyOnWriteArrayList<JConnection> list = connections.get(address);
        if (list != null) {
            for (JConnection c : list) {
                c.setReconnect(false);
            }
            logger.warn("Cancel reconnect to: {}.", address);
        }
    }

    /**
     * 取消对所有地址的自动重连
     */
    public void cancelAllReconnect() {
        for (UnresolvedAddress address : connections.keySet()) {
            cancelReconnect(address);
        }
    }
}
