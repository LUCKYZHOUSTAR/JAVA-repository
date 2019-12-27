package basic.test.test1.test2.test3;


import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumerExample {

    public static void main(String[] args) throws IOException {
        Person person = new Person();
        new Thread(new Consumer(person), "消费者一").start();
        new Thread(new Consumer(person), "消费者二").start();
        new Thread(new Consumer(person), "消费者三").start();

        new Thread(new Producer(person), "生产者一").start();
        new Thread(new Producer(person), "生产者一").start();
        new Thread(new Producer(person), "生产者一").start();
    }
}

class Producer implements Runnable {
    private Person person;

    public Producer(Person person) {
        this.person = person;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            person.produce();
        }

    }

}

class Consumer implements Runnable {

    private Person person;

    public Consumer(Person person) {
        this.person = person;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            person.consume();
        }

    }

}

class Person {
    private int foodNum = 0;

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private final int MAX_NUM = 5;

    public void produce() {
        lock.lock();
        try {
            while (foodNum == MAX_NUM) {
                System.out.println("box is full，size = " + foodNum);
                condition.await();
            }
            foodNum++;
            System.out.println("produce success foodNum = " + foodNum);
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
            while (foodNum == 0) {
                System.out.println("box is empty,size = " + foodNum);
                condition.await();
            }
            foodNum--;
            System.out.println("consume success foodNum = " + foodNum);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}