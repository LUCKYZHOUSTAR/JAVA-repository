//package basic.thread;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.CancellationException;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//import java.util.concurrent.locks.LockSupport;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:
// * @Date:Create in 14:02 2017/11/30
// */
//public class InvokeResult<V> {
//
//
//    public static void main(String[] args) {
//        System.out.println("hah ");
//    }
//
//
//
//
//    private volatile int state;
//    private static final int NEW = 0;
//    private static final int COMPLETING = 1;
//    private static final int NORMAL = 2;
//    private static final int EXCEPTIONAL = 3;
//    private static final int CANCELLED = 4;
//    private static final int INTERRUPTING = 5;
//    private static final int INTERRUPTED = 6;
//
//
//    /**
//     * The result to return or exception to throw from get()
//     */
//    private Object outcome; // non-volatile, protected by state reads/writes
//    /**
//     * The thread running the callable; CASed during run()
//     */
//    private volatile Thread runner;
//    /**
//     * Treiber stack of waiting threads
//     */
//    private volatile WaitNode waiters;
//
//    /**
//     * Returns result or throws exception for completed task.
//     *
//     * @param s completed state value
//     */
//    @SuppressWarnings("unchecked")
//    private V report(int s) throws ExecutionException {
//        Object x = outcome;
//        if (s == NORMAL)
//            return (V) x;
//        if (s >= CANCELLED)
//            throw new CancellationException();
//        throw new ExecutionException((Throwable) x);
//    }
//
//    public InvokeResult() {
//
//        this.state = NEW;       // ensure visibility of callable
//    }
//
//
//    public boolean isCancelled() {
//        return state >= CANCELLED;
//    }
//
//    public boolean isDone() {
//        return state != NEW;
//    }
//
//    public boolean cancel(boolean mayInterruptIfRunning) {
//        if (!(state == NEW &&
//                UNSAFE.compareAndSwapInt(this, stateOffset, NEW,
//                        mayInterruptIfRunning ? INTERRUPTING : CANCELLED)))
//            return false;
//        try {    // in case call to interrupt throws exception
//            if (mayInterruptIfRunning) {
//                try {
//                    Thread t = runner;
//                    if (t != null)
//                        t.interrupt();
//                } finally { // final state
//                    UNSAFE.putOrderedInt(this, stateOffset, INTERRUPTED);
//                }
//            }
//        } finally {
//            finishCompletion();
//        }
//        return true;
//    }
//
//    /**
//     * @throws CancellationException {@inheritDoc}
//     */
//    public V get() throws InterruptedException, ExecutionException {
//        int s = state;
//        if (s <= COMPLETING)
//            s = awaitDone(false, 0L);
//        return report(s);
//    }
//
//    /**
//     * @throws CancellationException {@inheritDoc}
//     */
//    public V get(long timeout, TimeUnit unit)
//            throws InterruptedException, ExecutionException, TimeoutException {
//        if (unit == null)
//            throw new NullPointerException();
//        int s = state;
//        if (s <= COMPLETING &&
//                (s = awaitDone(true, unit.toNanos(timeout))) <= COMPLETING)
//            throw new TimeoutException();
//        return report(s);
//    }
//
//    /**
//     * Protected method invoked when this task transitions to state
//     * {@code isDone} (whether normally or via cancellation). The
//     * default implementation does nothing.  Subclasses may override
//     * this method to invoke completion callbacks or perform
//     * bookkeeping. Note that you can query status inside the
//     * implementation of this method to determine whether this task
//     * has been cancelled.
//     */
//    protected void done() {
//    }
//
//    /**
//     * Sets the result of this future to the given value unless
//     * this future has already been set or has been cancelled.
//     * <p>
//     * <p>This method is invoked internally by the {@link #run} method
//     * upon successful completion of the computation.
//     *
//     * @param v the value
//     */
//    protected void set(V v) {
//        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
//            outcome = v;
//            UNSAFE.putOrderedInt(this, stateOffset, NORMAL); // final state
//            finishCompletion();
//        }
//    }
//
//    /**
//     * Causes this future to report an {@link ExecutionException}
//     * with the given throwable as its cause, unless this future has
//     * already been set or has been cancelled.
//     * <p>
//     * <p>This method is invoked internally by the {@link #run} method
//     * upon failure of the computation.
//     *
//     * @param t the cause of failure
//     */
//    protected void setException(Throwable t) {
//        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
//            outcome = t;
//            UNSAFE.putOrderedInt(this, stateOffset, EXCEPTIONAL); // final state
//            finishCompletion();
//        }
//    }
//
//
//    /**
//     * Ensures that any interrupt from a possible cancel(true) is only
//     * delivered to a task while in run or runAndReset.
//     */
//    private void handlePossibleCancellationInterrupt(int s) {
//        // It is possible for our interrupter to stall before getting a
//        // chance to interrupt us.  Let's spin-wait patiently.
//        if (s == INTERRUPTING)
//            while (state == INTERRUPTING)
//                Thread.yield(); // wait out pending interrupt
//
//        // assert state == INTERRUPTED;
//
//        // We want to clear any interrupt we may have received from
//        // cancel(true).  However, it is permissible to use interrupts
//        // as an independent mechanism for a task to communicate with
//        // its caller, and there is no way to clear only the
//        // cancellation interrupt.
//        //
//        // Thread.interrupted();
//    }
//
//    /**
//     * Simple linked list nodes to record waiting threads in a Treiber
//     * stack.  See other classes such as Phaser and SynchronousQueue
//     * for more detailed explanation.
//     */
//    static final class WaitNode {
//        volatile Thread thread;
//        volatile WaitNode next;
//
//        WaitNode() {
//            thread = Thread.currentThread();
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
//                    if (next == null)
//                        break;
//                    q.next = null; // unlink to help gc
//                    q = next;
//                }
//                break;
//            }
//        }
//
//        done();
//    }
//
//    /**
//     * Awaits completion or aborts on interrupt or timeout.
//     *
//     * @param timed true if use timed waits
//     * @param nanos time to wait, if timed
//     * @return state upon completion
//     */
//    private int awaitDone(boolean timed, long nanos)
//            throws InterruptedException {
//        final long deadline = timed ? System.nanoTime() + nanos : 0L;
//        WaitNode q = null;
//        boolean queued = false;
//        for (; ; ) {
//            if (Thread.interrupted()) {
//                removeWaiter(q);
//                throw new InterruptedException();
//            }
//
//            int s = state;
//            if (s > COMPLETING) {
//                if (q != null)
//                    q.thread = null;
//                return s;
//            } else if (s == COMPLETING) // cannot time out yet
//                Thread.yield();
//            else if (q == null)
//                q = new WaitNode();
//            else if (!queued)
//                queued = UNSAFE.compareAndSwapObject(this, waitersOffset,
//                        q.next = waiters, q);
//            else if (timed) {
//                nanos = deadline - System.nanoTime();
//                if (nanos <= 0L) {
//                    removeWaiter(q);
//                    return state;
//                }
//                LockSupport.parkNanos(this, nanos);
//            } else
//                LockSupport.park(this);
//        }
//    }
//
//    /**
//     * Tries to unlink a timed-out or interrupted wait node to avoid
//     * accumulating garbage.  Internal nodes are simply unspliced
//     * without CAS since it is harmless if they are traversed anyway
//     * by releasers.  To avoid effects of unsplicing from already
//     * removed nodes, the list is retraversed in case of an apparent
//     * race.  This is slow when there are a lot of nodes, but we don't
//     * expect lists to be long enough to outweigh higher-overhead
//     * schemes.
//     */
//    private void removeWaiter(WaitNode node) {
//        if (node != null) {
//            node.thread = null;
//            retry:
//            for (; ; ) {          // restart on removeWaiter race
//                for (WaitNode pred = null, q = waiters, s; q != null; q = s) {
//                    s = q.next;
//                    if (q.thread != null)
//                        pred = q;
//                    else if (pred != null) {
//                        pred.next = s;
//                        if (pred.thread == null) // check for race
//                            continue retry;
//                    } else if (!UNSAFE.compareAndSwapObject(this, waitersOffset,
//                            q, s))
//                        continue retry;
//                }
//                break;
//            }
//        }
//    }
//
//
//    private static Unsafe getUnsafe() throws Throwable {
//        Class<?> unsafeClass = Unsafe.class;
//        for (Field f : unsafeClass.getDeclaredFields()) {
//            if ("theUnsafe".equals(f.getName())) {
//                f.setAccessible(true);
//                return (Unsafe) f.get(null);
//            }
//        }
//        throw new IllegalAccessException("no declared field: theUnsafe");
//    }
//
//    // Unsafe mechanics
//    private static final sun.misc.Unsafe UNSAFE;
//    private static final long stateOffset;
//    private static final long runnerOffset;
//    private static final long waitersOffset;
//
//    static {
//        try {
//            UNSAFE = getUnsafe();
//            Class<?> k = InvokeResult.class;
//            stateOffset = UNSAFE.objectFieldOffset
//                    (k.getDeclaredField("state"));
//            runnerOffset = UNSAFE.objectFieldOffset
//                    (k.getDeclaredField("runner"));
//            waitersOffset = UNSAFE.objectFieldOffset
//                    (k.getDeclaredField("waiters"));
//        } catch (Throwable e) {
//            throw new Error(e);
//        }
//    }
//
//}
