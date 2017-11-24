package basic.thread;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 20:16 2017/11/1
 */
public class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger id = new AtomicInteger();
    private final String name;
    private final boolean daemon;
    private final int priority;
    private final ThreadGroup group;
    public NamedThreadFactory(String name) {
        this(name, false, Thread.NORM_PRIORITY);
    }

    public NamedThreadFactory(String name, boolean daemon) {
        this(name, daemon, Thread.NORM_PRIORITY);
    }

    public NamedThreadFactory(String name, int priority) {
        this(name, false, priority);
    }

    public NamedThreadFactory(String name, boolean daemon, int priority) {
        this.name = name + " #";
        this.daemon = daemon;
        this.priority = priority;
        SecurityManager s = System.getSecurityManager();
        group = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }



    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}
