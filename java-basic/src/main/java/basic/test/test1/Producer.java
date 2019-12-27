package basic.test.test1;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    //队列
    private BlockingQueue<Good> queue;
    private int maxSize;

    public Producer(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new ArrayBlockingQueue<>(maxSize);

    }


    public void addGood(Good good) {

        if (queue.size() >= maxSize) {

            throw new IllegalStateException("队列已经超限");
        }
        queue.add(good);
    }


    public void addGood(List<Good> goodList) {
        queue.addAll(goodList);
    }

    @Override
    public void run() {
        while (true) {
            Good goods = new Good();
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    try {
                        //队列已经满了
                        queue.wait();
                    } catch (InterruptedException e) {
                        //log exception
                    }
                }

                queue.add(goods);
                queue.notifyAll();
            }
        }
    }

}