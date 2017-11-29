package basic.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:41 2017/11/28
 */
public class Cache {

    static Map<String, String> map = new HashMap<>();
    static ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();
    static Lock r = rw1.readLock();
    static Lock w = rw1.writeLock();


    public static final Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }


    public static final String put(String key, String obf) {
        w.lock();
        try {

            return map.put(key, obf);
        } finally {
            w.unlock();
        }
    }

    // 清空所有的内容
    public static final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}
