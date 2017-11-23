package basic.thread;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:55 2017/11/23
 */
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);

    void shutDown();

    void addWorker(int num);
    void removeWorker(int num);

    int getJobSize();
}
