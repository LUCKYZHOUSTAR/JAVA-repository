package com.sharing;

/**
 * @Author:chaoqiang.zhou
 * @Description:参考：https://www.cnblogs.com/Binhua-Liu/p/5620339.html 所有的Java对象都有8字节的对象头，
 * 前四个字节用来保存对象的哈希码和锁的状态，
 * 前3个字节用来存储哈希码，最后一个字节用来存储锁状态，一旦对象上锁，这4个字节都会被拿出对象外，
 * 并用指针进行链接。剩下4个字节用来存储对象所属类的引用。对于数组来讲，还有一个保存数组大小的变量，为4字节。
 * 每一个对象的大小都会对齐到8字节的倍数，不够8字节部分需要填充。
 * 为了保证效率，Java编译器在编译Java对象的时候，通过字段类型对Java对象的字段进行排序
 * 解决伪共享的办法是使用缓存行填充，使一个对象占用的内存大小刚好为64bytes或它的整数倍，这样就保证了一个缓存行里不会有多个对象。《剖析Disruptor:为什么会这么快？(三)伪共享》提供了缓存行填充的例子：
 * @Date:Create in 15:01 2018/1/8
 */
public class Java6 implements Runnable {
    public final static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    public Java6(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Java6(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }


    /**
     * VolatileLong通过填充一些无用的字段p1,p2,p3,p4,p5,p6，再考虑到对象头也占用8bit,
     * 刚好把对象占用的内存扩展到刚好占64bytes（或者64bytes的整数倍）。这样就避免了一个缓存行中加载多个对象。
     * 但这个方法现在只能适应JAVA6 及以前的版本了。
     */
    public final static class VolatileLong {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6; // comment out
    }
}
