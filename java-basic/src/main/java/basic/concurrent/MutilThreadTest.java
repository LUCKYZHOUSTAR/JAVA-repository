//package basic.concurrent;
//
//import basic.atomic.AtomicUpdater;
//
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
//import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:什么时候需要考虑线程并发的问题 以后向大佬学习，这种并发的控制，现在终于对线程并发，有了一定的了解
// * @Date:Create in 9:55 2018/1/19
// */
//public class MutilThreadTest {
//
//    /**
//     * 平时在写代码的过程中很少注意过这些东西，下面来分析一下
//     * 所谓的并发，也就是多个线程之间操作一个东西，比如多个线程并发操作一个实例对象中的值，就需要考虑并发的问题，
//     * 多个线程并发操作一个类的静态变量，也需要考虑并发的问题，那么如何解决并发的问题呢
//     */
//
//    /*cas原理，获取到该list中array对象的内存地址,对象都用这个来获取*/
//    private static final AtomicReferenceFieldUpdater<CopyOnWriteArrayList, Object[]> channelsUpdater =
//            AtomicUpdater.newAtomicReferenceFieldUpdater(CopyOnWriteArrayList.class, Object[].class, "array");
//
//    /**
//     * 基本类型都这样来获取
//     */
//    private static final AtomicIntegerFieldUpdater<MutilThreadTest> signalNeededUpdater =
//            AtomicIntegerFieldUpdater.newUpdater(MutilThreadTest.class, "signalNeeded");
//    private static final AtomicIntegerFieldUpdater<MutilThreadTest> indexUpdater =
//            AtomicIntegerFieldUpdater.newUpdater(MutilThreadTest.class, "index");
//    /**
//     * 线程安全的集合，内部也是锁来实现
//     */
//    private final CopyOnWriteArrayList<NettyChannel> channels = new CopyOnWriteArrayList<>();
//
//    /**
//     * 保证各个线程的可见性，是否需要唤醒操作
//     */
//    private volatile int signalNeeded = 0; // 0: false, 1: true
//
//    /**
//     * 可重入锁
//     */
//    private final ReentrantLock lock = new ReentrantLock();
//    /**
//     * 信号变量
//     */
//    private final Condition notifyCondition = lock.newCondition();
//    private volatile int index = 0;
//
//
//    public NettyChannel next() {
//        for (; ; ) {
//            // snapshot of channels array
//            Object[] elements = channelsUpdater.get(channels);
//            int length = elements.length;
//            if (length == 0) {
//                if (waitForArailable(1000)) {
//                    continue;
//                }
//                throw new IllegalArgumentException("no channel");
//            }
//
//            if (length == 1) {
//                return (NettyChannel) elements[0];
//            }
//
//            int index = indexUpdater.getAndIncrement(this) & Integer.MAX_VALUE;
//            return (NettyChannel) elements[index % length];
//        }
//
//
//    }
//
//    public boolean add(NettyChannel nettyChannel) {
//
//        //线程安全的
//        boolean added = channels.add(nettyChannel);
//        //下面还需要唤醒等待的锁操作
//        if (added) {
//            //通过该字段来判断是否需要唤醒，如果更新成功，代表需要唤醒，否则返回原始值
//            if (signalNeededUpdater.getAndSet(this, 0) != 0) {
//                //jdk标准用法，执行都是时候只能有一个owner
//                final ReentrantLock _look = lock;
//                _look.lock();
//                try {
//                    notifyCondition.signalAll();
//                } finally {
//                    _look.unlock();
//                }
//            }
//        }
//
//        return added;
//    }
//
//    /**
//     * 通过线程安全的集合来实现多个线程操作共享变量
//     *
//     * @param channel
//     * @return
//     */
//    public boolean remove(NettyChannel channel) {
//        return channels.remove(channel);
//    }
//
//    public int size() {
//        return channels.size();
//    }
//
//
//    public boolean waitForArailable(long timeoutMillis) {
//        //读的时候是不加锁的，通过volatile语义，保证线程之间的可见性
//        boolean available = isAvailable();
//        if (available) {
//            return true;
//        }
//
//        long remains = TimeUnit.MILLISECONDS.toNanos(timeoutMillis);
//
//        //为何用这句话，看final的关键字，stack copy比非stack copy性能要高
//        final ReentrantLock _look = lock;
////        this.lock.lock();
//        _look.lock();
//        try {
//
//            //这块为什么要用循环，这样做的目的是让该阻塞的线程，唤醒之后，再走一遍while，进行多一次的判断
//            while (!(available = isAvailable())) {
//                signalNeeded = 1;//set signal needed to true
//                if ((remains = notifyCondition.awaitNanos(remains)) <= 0) {
//                    break;
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            _look.unlock();
//        }
//        return available;
//    }
//
//    public boolean isAvailable() {
//        return !channels.isEmpty();
//    }
//}
