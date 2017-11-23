package basic.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description: * 添加一个Job后，对工作队列jobs调用了其notify()方法，而不是notifyAll()方法，因为能够
 * 确定有工作者线程被唤醒，这时使用notify()方法将会比notifyAll()方法获得更小的开销（避免
 * 将等待队列中的线程全部移动到阻塞队列中）。
 * 可以看到，线程池的本质就是使用了一个线程安全的工作队列连接工作者线程和客户端
 * 线程，客户端线程将任务放入工作队列后便返回，而工作者线程则不断地从工作队列上取出
 * 工作并执行。当工作队列为空时，所有的工作者线程均等待在工作队列上，当有客户端提交了
 * 一个任务之后会通知任意一个工作者线程，随着大量的任务被提交，更多的工作者线程会被
 * 唤醒。
 * @Date:Create in 17:57 2017/11/23
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {


    // 线程池最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;
    // 线程池默认的数量
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    // 线程池最小的数量
    private static final int MIN_WORKER_NUMBERS = 1;
    //维护工作的列表操作
    private final LinkedList<Job> jobs = new LinkedList<>();
    //维护工作的列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    // 工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    private AtomicLong threadIndex = new AtomicLong(0);

    public DefaultThreadPool() {
        initializeWokers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        if (num > MAX_WORKER_NUMBERS) {
            num = MAX_WORKER_NUMBERS;
        }
        if (num < MIN_WORKER_NUMBERS) {
            num = MIN_WORKER_NUMBERS;
        }
        initializeWokers(workerNum);
    }

    // 初始化线程工作者
    private void initializeWokers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worder-" + threadIndex.getAndIncrement());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                //添加一个Job后，对工作队列jobs调用了其notify()方法，而不是notifyAll()方法，因为能够
//                确定有工作者线程被唤醒，这时使用notify()方法将会比notifyAll()方法获得更小的开销（避免
//                将等待队列中的线程全部移动到阻塞队列中）。
                jobs.notify();
            }
        }
    }

    @Override
    public void shutDown() {
        workers.forEach(worker -> worker.shutdown());
    }

    @Override
    public void addWorker(int num) {
        synchronized (jobs) {
            // 限制新增的Worker数量不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWokers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {

        synchronized (jobs) {
            if (num > this.workerNum) {
                throw new IllegalArgumentException("beyond worknum");
            }
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    //工作者负责消费任务,也就是并发的从集合中进行获取任务然后执行，当没有任务的时候，就会进行阻塞操作
    class Worker implements Runnable {

        //是否工作，保持线程的可见性
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                //锁定任务的集合操作
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            //如果没有任务的话，此时就进行阻塞操作
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知到外部对WorkerTHread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    //取出一个任务
                    job = jobs.removeFirst();
                    if (job != null) {
                        try {
                            job.run();
                        } catch (Exception e) {
                            //忽略job中执行的异常信息
                        }

                    }
                }
            }

        }

        public void shutdown() {
            running = false;
        }

    }
}
