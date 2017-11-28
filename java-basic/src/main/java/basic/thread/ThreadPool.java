package basic.thread;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:55 2017/11/23
 */
public interface ThreadPool<Job extends Runnable> {

    static final long DEFAULT_SHUTDOWN_QUIET_PERIOD = 2;
    static final long DEFAULT_SHUTDOWN_TIMEOUT = 15;

    void execute(Job job);

    void shutDown();

    void addWorker(int num);

    void removeWorker(int num);

    int getJobSize();


    void shutdownGracefully();
}
