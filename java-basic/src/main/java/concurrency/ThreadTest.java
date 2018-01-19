package concurrency;

import java.util.LinkedList;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:36 2018/1/19
 */
public class ThreadTest {


    private static LinkedList<String> obj = new LinkedList<>();

    /**
     * block状态
     *
     * @param args
     */
    public static void main(String[] args) {

        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> {
                get();
            }, "name" + i);
            thread.start();
        }
    }


    public void testStatus() {
        //传入runnable对象---状态--NEW
        Thread threadA = new Thread(() -> {

            //do someting
        });

        //状态-->RUNNABLE   等待被CPU分配执行的时间来调度
        threadA.start();


        //继承的方式
        new Thread() {
            @Override
            public void run() {
                //do something
            }
        }.start();
    }

    public static synchronized void get() {

        try {
            Thread.sleep(Integer.MAX_VALUE);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void addJob(String job) {
        if (job != null) {
            synchronized (obj) {
                obj.add(job);
                obj.notifyAll();
            }
        }

    }

    class Worker implements Runnable {
        //是否工作，保持线程的可见性
        private volatile boolean running = true;

        @Override
        public void run() {

            while (running) {
                String job = null;
                synchronized (obj) {
                    while (obj.isEmpty()) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    job = obj.removeFirst();
                    //do somethong
                }
            }
        }
    }

}
