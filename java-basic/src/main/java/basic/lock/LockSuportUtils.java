package basic.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author:chaoqiang.zhou
 * @Description:也是用来锁定对象操作 详情见futuretask的用法，是用来锁定某个线程的
 * @Date:Create in 16:50 2017/11/29
 */
public class LockSuportUtils {


    public static void main(String[] args) throws Exception {


        Thread thread = new Thread(new run());
        thread.start();

        Thread.sleep(2000);
        LockSupport.unpark(thread);
    }


    static class run implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                if (i == 999) {
                    LockSupport.park(this);
                }
                System.out.println("hahaha" + i);
            }

        }
    }

    static class Remote {
        //保证线程的可见性
        private volatile Integer result;

        public void get() {


        }

        public void set(Integer res) {
            this.result = res;
//            LockSupport.unpark(this);
        }
    }
}
