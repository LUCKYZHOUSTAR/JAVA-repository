package basic.thread;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:07 2017/12/14
 */
public class ThreadDaemon {

    public static void main(String[] args) throws Exception {
        Thread  t=new Thread(()->{
            try {
                System.out.println("我开始了");
                Thread.currentThread().join();
            }catch (Exception e){

                System.out.println("出现异常了");

            }finally {
                System.out.println("我关闭了");
            }
        });

        t.setDaemon(true);
        t.start();

        Thread.sleep(3000L);
        System.out.println("程序结束了");
    }

}
