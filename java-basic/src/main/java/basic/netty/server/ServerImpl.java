package basic.netty.server;

import basic.netty.common.ThreadPoolCore;
import basic.netty.data.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:26 2017/11/28
 */
public class ServerImpl implements Server {


    private final LinkedList<Request> consumerJobs = new LinkedList<>();
    private ConsumerProcessor consumerProcessor;
    private ThreadPoolCore poolCore;

    private List<Worker> workers = new CopyOnWriteArrayList<>();


    public ServerImpl(ConsumerProcessor consumerProcessor) {
        this(consumerProcessor, new ThreadPoolCore());
    }

    public ServerImpl(ConsumerProcessor consumerProcessor, ThreadPoolCore core) {
        this.consumerProcessor = consumerProcessor;
        this.poolCore = core;
        this.start();
    }

    @Override
    public void start() {
        //开启线程操作，会自动的执行任务信息
        for (int i = 0; i < poolCore.getnThreads(); i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread workerThread = poolCore.getThreadFactory().newThread(worker);
            workerThread.start();
        }
    }

    @Override
    public void shutDown() {
        workers.forEach((worker) -> worker.shutDown());
    }

    @Override
    public void execute(Request request) {

        if (request != null) {
            synchronized (consumerJobs) {
                //这块写的不太好
                consumerJobs.addLast(request);
                consumerJobs.notify();
            }

        }
    }

    @Override
    public void execute(List job) {
        consumerJobs.addAll(job);
    }


    public class Worker implements Runnable {
        //线程的状态标识
        private volatile boolean running = true;

        @Override
        public void run() {
            Request job = null;
            while (running) {
                synchronized (consumerJobs) {
                    //没有要处理的任务信息，就让线程处于等待阶段
                    while (consumerJobs.isEmpty()) {
                        try {
                            consumerJobs.wait();
                        } catch (InterruptedException e) {
                            //忽略该中断的异常信息，外部中断会清楚中断的标志,因此要手动清楚
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = consumerJobs.removeFirst();
                    if (job != null) {
                        try {
                            //执行该job操作
                            consumerProcessor.handleRequest(job);
                        } catch (Exception e) {
                            //执行该handler异常信息操作
                            consumerProcessor.handleException(job, e);
                        }
                    }
                }
            }
        }


        public void shutDown() {
            this.running = false;
        }
    }

}
