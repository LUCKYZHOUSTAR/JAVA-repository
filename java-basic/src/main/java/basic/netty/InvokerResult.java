package basic.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description://用来缓存结果信息
 * @Date:Create in 14:40 2017/11/28
 */
public class InvokerResult<V> {

    //通过请求的标识来进行缓存数据信息
    public static final ConcurrentMap<Long, InvokerResult> future = new ConcurrentHashMap<>();
    private Object outcome;

    private CountDownLatch countDownLatch = new CountDownLatch(1);


    public void set(V v) {
        outcome = v;
        countDownLatch.countDown();
    }

    public V getResult() throws Throwable {

        return get(2000, TimeUnit.MILLISECONDS);
    }

    public V get(long timeOut, TimeUnit unit) throws Throwable {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        //同步等待结果信息
        countDownLatch.await();
        return (V) outcome;
    }
}
