package org.jupiter.transport.channel;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:21 2018/1/18
 */
public class CopyOnWriteGroupList {
    private static final JChannelGroup[] EMPTY_ARRAY = new JChannelGroup[0];


    private transient final ReentrantLock lock = new ReentrantLock();

    private final DirectoryJChannelGroup parent;

    private volatile transient JChannelGroup[] array;
    private transient boolean sameWeight; // 无volatile修饰, 通过array保证可见性

    public CopyOnWriteGroupList(DirectoryJChannelGroup parent) {
        this.parent = parent;
        setArray(EMPTY_ARRAY);
    }


    public final JChannelGroup[] snapshot() {
        return getArray();
    }

    public final void setSameWeight(boolean sameWeight) {
        JChannelGroup[] elements = getArray();
        setArray(elements, sameWeight); // ensures volatile write semantics
    }

    public final boolean isSameWeight() {
        // first read volatile
        return getArray().length == 0 || sameWeight;
    }


    final void setArray(JChannelGroup[] a) {
        sameWeight = false;
        array = a;
    }

    final void setArray(JChannelGroup[] a, boolean sameWeight) {
        this.sameWeight = sameWeight;
        array = a;
    }

    final JChannelGroup[] getArray() {
        return array;
    }
}
