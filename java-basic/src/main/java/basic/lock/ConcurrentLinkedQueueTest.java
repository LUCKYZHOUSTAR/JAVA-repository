package basic.lock;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author:chaoqiang.zhou
 * @Description:cas算法，高并发的队列实现,先进来，先出来
 * @Date:Create in 18:59 2017/11/28
 */
public class ConcurrentLinkedQueueTest {


    /**
     * peek()获取元素 不移除头结点
     * poll() 获取元素并且在队列中移除，如果队列为空返回null
     *
     * @param args
     */
    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<String>();
        concurrentLinkedQueue.add("a");
        concurrentLinkedQueue.add("b");
        concurrentLinkedQueue.add("c");
        concurrentLinkedQueue.offer("d"); // 将指定元素插入到此队列的尾部。
        concurrentLinkedQueue.peek(); // 检索并移除此队列的头，如果此队列为空，则返回 null。
        concurrentLinkedQueue.poll(); // 检索并移除此队列的头，如果此队列为空，则返回 null。

        for (String str : concurrentLinkedQueue) {
            System.out.println(str);
        }
    }
}
