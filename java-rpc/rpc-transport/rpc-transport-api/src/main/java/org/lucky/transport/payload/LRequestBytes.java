package org.lucky.transport.payload;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:04 2018/2/7
 */
public class LRequestBytes extends BytesHolder{

    private static final AtomicLong sequence = new AtomicLong();


    private final long invokeId;

    private transient long timestamp;
    public LRequestBytes() {
        this(sequence.incrementAndGet());
    }

    public LRequestBytes(long invokeId) {
        this.invokeId = invokeId;
    }

    public long invokeId() {
        return invokeId;
    }

    public long timestamp() {
        return timestamp;
    }

    public void timestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
