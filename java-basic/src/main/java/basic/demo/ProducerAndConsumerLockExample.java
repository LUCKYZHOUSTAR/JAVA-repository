package basic.demo;


import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumerLockExample {

    public static void main(String[] args) throws IOException {
        Goods goods = new Goods();
        new Thread(new Consumer(goods), "消费者一").start();
        new Thread(new Consumer(goods), "消费者二").start();
        new Thread(new Consumer(goods), "消费者三").start();

        new Thread(new Producer(goods), "生产者一").start();
        new Thread(new Producer(goods), "生产者一").start();
        new Thread(new Producer(goods), "生产者一").start();
    }
}

class Producer implements Runnable {
    private Goods goods;

    public Producer(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            goods.produce();
        }

    }

}

class Consumer implements Runnable {

    private Goods goods;

    public Consumer(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            goods.consume();
        }

    }

}

class Goods {
    private int num = 0;

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private final int MAX_NUM = 5;

    public void produce() {
        lock.lock();
        try {
            while (num == MAX_NUM) {
                condition.await();
            }
            num++;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void consume() {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            num--;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}