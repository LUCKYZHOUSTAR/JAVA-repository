package basic.runtime;

/**
 * @Author:chaoqiang.zhou
 * @Description:在看别人的代码时，发现其中有这个方法，便顺便梳理一下。 void java.lang.Runtime.addShutdownHook(Thread hook)
 * 该方法用来在jvm中增加一个关闭的钩子。当程序正常退出,系统调用 System.exit方法或虚拟机被关闭时才会执行添加的shutdownHook线程。
 * 其中shutdownHook是一个已初始化但并不有启动的线程，当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，
 * 当系统执行完这些钩子后，jvm才会关闭。所以可通过这些钩子在jvm关闭的时候进行内存清理、资源回收等工作。
 * @Date:Create in 11:09 2017/12/7
 */
public class TestRuntimeShutdownHook {
    public static void main(String[] args) {

        Thread shutdownHookOne = new Thread() {
            @Override
            public void run() {
                System.out.println("shutdownHook one...");
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHookOne);

        Runnable threadOne = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread one doing something...");
            }
        };

        Runnable threadTwo = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread two doing something...");
            }
        };

        threadOne.run();
        threadTwo.run();
    }
}
