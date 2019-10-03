package com.lucky.code.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/10/3 16:22
 * @Description:
 */
public class Foo2 {
    ReentrantLock lock = new ReentrantLock();
    Condition firstCondition = lock.newCondition();
    Condition secondCondition = lock.newCondition();
    Condition thirdCondition = lock.newCondition();
    int state = 1;

    public Foo2() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        lock.lock();
        try {
            // printFirst.run() outputs "first". Do not change or remove this line.
            if (state != 1) {
                firstCondition.await();
            }
            state = 2;
            printFirst.run();
            secondCondition.signal();
        } finally {
            lock.unlock();
        }

    }

    public void second(Runnable printSecond) throws InterruptedException {
        lock.lock();
        try {
            if (state != 2) {
                secondCondition.await();
            }
            state = 3;
            printSecond.run();

            thirdCondition.signal();
            // printSecond.run() outputs "second". Do not change or remove this line.
        } finally {
            lock.unlock();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        try {
            if (state != 3) {
                thirdCondition.await();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        } finally {
            lock.unlock();
        }
    }

}
