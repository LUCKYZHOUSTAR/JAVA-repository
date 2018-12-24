package basic.netty.common;

import java.util.concurrent.ThreadFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:管理核心线程的操作类信息
 * @Date:Create in 13:37 2017/11/28
 */

public class ThreadPoolCore {


    private static final int DEFAULT_EVENT_LOOP_THREADS = Runtime.getRuntime().availableProcessors() * 2;


    private int nThreads;
    private ThreadFactory threadFactory;


    public ThreadPoolCore() {
        this(0);
    }

    public ThreadPoolCore(int nThreads) {

        this(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, new DefaultThreadFactory());
    }

    public ThreadPoolCore(int nThreads, ThreadFactory threadFactory) {
        this.nThreads = nThreads;
        this.threadFactory = threadFactory;
    }


    public int getnThreads() {
        return nThreads;
    }

    public void setnThreads(int nThreads) {
        this.nThreads = nThreads;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }
}
