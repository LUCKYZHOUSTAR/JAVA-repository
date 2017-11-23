package basic.thread;

import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:12 2017/11/23
 */
public class DefaultThreadPoolTest {


    public static void main(String[] args) {

        DefaultThreadPool<Job> defaultThreadPool = new DefaultThreadPool<>();
        for (int i = 0; i < 1000; i++) {
            Job job = new Job(i);
            defaultThreadPool.execute(job);
        }
    }

    @Test
    public void test1() {

        DefaultThreadPool<Job> defaultThreadPool = new DefaultThreadPool<>();


        for (int i = 0; i < 1000; i++) {
            Job job = new Job(i);
            defaultThreadPool.execute(job);
        }
    }


    static class Job implements Runnable {

        private int num;

        Job(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "我想飞" + num);
        }
    }
}
