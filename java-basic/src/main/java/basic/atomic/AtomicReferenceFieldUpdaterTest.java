package basic.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author:chaoqiang.zhou
 * @Description: AtomicReferenceFieldUpdater
 * 一个基于反射的工具类，它能对指定类的指定的volatile引用字段进行原子更新。(注意这个字段不能是private的)
 * @Date:Create in 19:00 2017/11/1
 */
public class AtomicReferenceFieldUpdaterTest {


    public static void main(String[] args) {
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Dog.class, String.class, "name");
        Dog dog1 = new Dog();
        updater.compareAndSet(dog1, dog1.name, "test");
        System.out.println(dog1.name);
    }


    public static class Dog {
        volatile String name = "dog1";

    }

    static class AtomicFieldIncr {

        //这里最好是public volatile，不要加final、static
        //修饰符最好也不要为protected，private，涉及调用者访问被调用者的access问题
        public volatile int idx;

        public AtomicFieldIncr() {
        }

        public int getIdx() {
            //0,-1返回Reflection本身
//            System.out.println(Reflection.getCallerClass(0));
//            System.out.println(Reflection.getCallerClass(-1));
//            //1返回自己
//            System.out.println(Reflection.getCallerClass(1));
//            //2，空返回调用者
//            System.out.println(Reflection.getCallerClass(2));
//            System.out.println(Reflection.getCallerClass());
            return this.idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }
    }
}
