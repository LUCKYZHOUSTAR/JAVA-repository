//package basic.threadpool;
//
//import java.util.concurrent.*;
//import java.util.concurrent.locks.LockSupport;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:
// * @Date:Create in 11:19 2017/11/29
// */
//public class FutureTaskCode<V> implements RunnableFuture<V> {
//
//
//    private volatile int state;
//    /**
//     * * Possible state transitions:
//     * NEW -> COMPLETING -> NORMAL
//     * NEW -> COMPLETING -> EXCEPTIONAL
//     * NEW -> CANCELLED
//     * NEW -> INTERRUPTING -> INTERRUPTED
//     */
//    private static final int NEW = 0;
//    private static final int COMPLETING = 1;
//    private static final int NORMAL = 2;
//    private static final int EXCEPTIONAL = 3;
//    private static final int CANCELLED = 4;
//    private static final int INTERRUPTING = 5;
//    private static final int INTERRUPTED = 6;
//
//    private Callable<V> callable;
//    private Object outcome;
//
//    private volatile Thread runner;
//    private volatile WaitNode waiters;
//
//    private V report(int s) throws ExecutionException {
//        Object x = outcome;
//        if (s == NORMAL) {
//            return (V) x;
//        }
//
//        if (s >= CANCELLED) {
//            throw new CancellationException();
//        }
//        throw new ExecutionException((Throwable) x);
//    }
//
//
//    public FutureTaskCode(Callable<V> callable) {
//        if (callable == null) {
//            throw new NullPointerException();
//        }
//        this.callable = callable;
//        this.state = NEW;
//    }
//
//
//    @Override
//    public boolean isCancelled() {
//        return state >= CANCELLED;
//    }
//
//    @Override
//    public boolean isDone() {
//        return state != NEW;
//    }
//
//
//    @Override
//    public boolean cancel(boolean mayInterruptIfRunning) {
//        //只有状态是新建，并且更新了状态的时候才会是取消成功
//        if (!(state == NEW && UNSAFE.compareAndSwapInt(this, stateOffset, NEW, mayInterruptIfRunning ? INTERRUPTING : CANCELLED))) {
//            return false;
//        }
//
//        try {// in case call to interrupt throws exception
//            if (mayInterruptIfRunning) {
//                try {
//                    Thread t = runner;
//                    if (t != null) {
//                        t.interrupt();
//                    }
//                } finally {// final state
//                    UNSAFE.putOrderedInt(this, stateOffset, INTERRUPTED);
//                }
//            }
//        } finally {
//            finishCompletion();
//        }
//
//        return true;
//    }
//
//
//    public V get() throws InterruptedException, ExecutionException {
//        int s = state;
//        if (s <= COMPLETING) {
//
//        }
//    }
//
//
//    private int awaitDone(boolean timed, long nanos) {
//        final long deadline = timed ? System.nanoTime() + nanos : 0L;
//        WaitNode q = null;
//        boolean queued = false;
//        for (; ; ) {
//            if (Thread.interrupted()) {
//
//            }
//        }
//    }
//
//    /**
//     * Removes and signals all waiting threads, invokes done(), and
//     * nulls out callable.
//     */
//    private void finishCompletion() {
//        // assert state > COMPLETING;
//        for (WaitNode q; (q = waiters) != null; ) {
//            if (UNSAFE.compareAndSwapObject(this, waitersOffset, q, null)) {
//                for (; ; ) {
//                    Thread t = q.thread;
//                    if (t != null) {
//                        q.thread = null;
//                        LockSupport.unpark(t);
//                    }
//                    WaitNode next = q.next;
//                    if (next == null) {
//                        break;
//                    }
//                    //把next变为null，自己再变为next接着执行
//                    q.next = null;//unlink to help gc
//                    q = next;
//                }
//                break;
//            }
//        }
//        done();
//        callable = null;        // to reduce footprint
//    }
//
//    protected void done() {
//    }
//
//    public FutureTaskCode(Runnable runnable, V result) {
//        this.callable = Executors.callable(runnable, result);
//        this.state = NEW;
//    }
//
//
//    static final class WaitNode {
//        volatile Thread thread;
//        volatile WaitNode next;
//
//        WaitNode() {
//            thread = Thread.currentThread();
//        }
//
//        ;
//    }
//
//
//    private void removeWaiter(WaitNode node) {
//        if (node != null) {
//            node.thread = null;
//            retry:
//            for (; ; ) {
//                // restart on removeWaiter race
//                for (WaitNode pred = null, q = waiters, s; q != null; q = s) {
//                    s = q.next;
//                    if (q.thread != null) {
//                        pred = q;
//                    } else if (pred != null) {
//                        pred.next = s;
//                        if (pred.thread == null) {
//                            continue retry;
//                        }
//                    } else if (!UNSAFE.compareAndSwapObject(this, waitersOffset, q, s)) {
//                        continue retry;
//                    }
//                }
//
//                break;
//            }
//        }
//    }
//
//    private static final sun.misc.Unsafe UNSAFE;
//    private static final long stateOffset;
//    private static final long runnerOffset;
//    private static final long waitersOffset;
//
//    static {
//        try {
//            UNSAFE = sun.misc.Unsafe.getUnsafe();
//            Class<?> k = FutureTaskCode.class;
//            //得到偏移量
//            stateOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("state"));
//            runnerOffset = UNSAFE.objectFieldOffset
//                    (k.getDeclaredField("runner"));
//            waitersOffset = UNSAFE.objectFieldOffset
//                    (k.getDeclaredField("waiters"));
//        } catch (Exception e) {
//            throw new Error(e);
//        }
//    }
//
//}
