package basic.demo.consumer;


public class ProducerConsumerExample {

    public static void main(String[] args) {
        GoodsChannel c = new GoodsChannel();
        Producer p1 = new Producer(c, 1);
        Consumer c1 = new Consumer(c, 1);
        p1.start();
        c1.start();
    }
}

class GoodsChannel {
    private int contents;
    private boolean available = false;

    public synchronized int get() {
        while (available == false) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        available = false;
        notifyAll();
        return contents;
    }

    public synchronized void put(int value) {
        while (available == true) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        contents = value;
        available = true;
        notifyAll();
    }
}

class Consumer extends Thread {
    private GoodsChannel goods;
    private int number;

    public Consumer(GoodsChannel c, int number) {
        goods = c;
        this.number = number;
    }

    @Override
    public void run() {
        int value = 0;
        for (int i = 0; i < 10; i++) {
            value = goods.get();
            System.out.println("消费者 #" + this.number + " got: " + value);
        }
    }
}

class Producer extends Thread {
    private GoodsChannel goodsChannel;
    private int number;

    public Producer(GoodsChannel c, int number) {
        goodsChannel = c;
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            goodsChannel.put(i);
            System.out.println("生产者 #" + this.number + " put: " + i);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
            }
        }
    }
}