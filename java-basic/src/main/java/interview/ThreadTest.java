package interview;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/8/8 17:43
 * @Description:
 */
public class ThreadTest {


    /**
     * 功能描述:
     *
     * 介绍: Thread的interrupt方法
     * 调用interrupt(),通知线程应该中断了
     * 1.如果该线程处于阻塞状态，那么线程将立即退出阻塞状态，并抛出一个interruptedException异常，并且会清除该标志位，还是false，例如处于sleep、wait，join等状态，会抛出该异常
     * 2.如果线程处于正常活动状态，那么就会将该线程的中断标志位设置为true，被设置为true的中断标志位不影响该线程的正常运行
     *
     * 使用: Thread的interrupt方法
     * 需要被调用的线程配合中断
     * 1.在正常运行任务时，经常检查本线程的中断标志位，如果被设置了中断标志位就自行停止线程
     *
     * 2.如果线程处于正常状态，那么就会将该线程的中断标志位设置为true，被设置为true的中断标志位不影响该线程的正常运行
     */

    public void interruptedTest() {

    }
}
