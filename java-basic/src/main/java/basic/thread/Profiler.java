package basic.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:20 2017/10/25
 */
public class Profiler {
    // 第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    //每一个线程缓存了一个副本，该副本之间相互的不影响
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {

        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws Exception {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }
}
