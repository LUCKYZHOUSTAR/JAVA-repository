package basic.netty.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:chaoqiang.zhou
 * @Description:默认的工厂
 * @Date:Create in 13:45 2017/11/28
 */
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger threadIndex = new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, String.format("NettyBossSelector_%d", threadIndex.getAndIncrement()));
    }
}
