package org.jupiter.transport;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:37 2018/1/18
 */
public class Acknowledge {

    private final long sequence;

    public Acknowledge(long sequence) {
        this.sequence = sequence;
    }

    public long sequence() {
        return sequence;
    }
}
