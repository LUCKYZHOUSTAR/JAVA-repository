package basic.thread;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:23 2017/10/25
 */
public class WaitingTimeOut {

    private Object result;

    // 对当前对象加锁
    public synchronized Object get(long mills) throws InterruptedException {
        long future = System.currentTimeMillis() + mills;
        long remaining = mills;
        // 当超时大于0并且result返回值不满足要求
        while ((result == null) && remaining > 0) {
            wait(remaining);
            remaining = future - System.currentTimeMillis();
        }
        return result;
    }


//    public synchronized Object get(long mills) throws InterruptedException {
//        long future = System.currentTimeMillis() + mills;
//        long remaing = mills;
//        while (result == null && remaing > 0) {
//            wait(remaing);
//            //剩余时间一直在减少
//            remaing = future - System.currentTimeMillis();
//        }
//
//        return result;
//    }
}
