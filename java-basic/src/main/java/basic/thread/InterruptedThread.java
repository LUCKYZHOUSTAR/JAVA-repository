package basic.thread;

/**
 * @Author:chaoqiang.zhou
 * @Description:对非阻塞中的线程中断的Demo:
 * @Date:Create in 10:44 2018/3/1
 */
public class InterruptedThread extends Thread {


  //非阻塞，因为该线程执行的方法里面没有调用object的阻塞方法，所以只是改变标志位
  //但是如果调用了await的方法，就会抛出一个中断的异常，进行标志位的清除，详情看example3的信息
  @Override
  public void run() {
    while (true) {
      if (Thread.currentThread().isInterrupted()) {
        System.out.println("Someone interrupted me.");
      } else {
        System.out.println("Thread is Going...");
      }
      try {
        Thread.sleep(3000l);
      } catch (InterruptedException e) {

        //在这里会立马的清除标志位的信息
      }
    }
  }

  /**
   *
   * 把握几个重点：stop变量、run方法中的sleep()、interrupt()、InterruptedException。串接起
   * 来就是这个意思：当我们在run方法中调用sleep（或其他阻塞线程的方法）时，如果线程阻塞的
   * 时间过长，比如10s，那在这10s内，线程阻塞，run方法不被执行，但是如果在这10s内，stop被
   * 设置成true，表明要终止这个线程，但是，现在线程是阻塞的，它的run方法不能执行，自然也就
   * 不能检查stop，所 以线程不能终止，这个时候，我们就可以用interrupt()方法了：我们在
   * thread.stop = true;语句后调用thread.interrupt()方法， 该方法将在线程阻塞时抛出一个中断
   * 信号，该信号将被catch语句捕获到，一旦捕获到这个信号，线程就提前终结自己的阻塞状态，这
   * 样，它就能够 再次运行run 方法了，然后检查到stop = true，while循环就不会再被执行，在执
   * 行了while后面的清理工作之后，run方法执行完 毕，线程终止。
   *
   */

  /**
   * 注意这个例子是非阻塞的线程，也就是该线程没有调用Object.await，thread.join和thread.sleep上
   */
  public static void main(String[] args) {
    //在main线程sleep的过程中由于t线程中isInterrupted()为false所以不断的输出”Thread is going”。
    // 当调用t线程的interrupt()后t线程中isInterrupted()为true。此时会输出Someone interrupted me.
    // 而且线程并不会因为中断信号而停止运行。因为它只是被修改一个中断信号而已。
    InterruptedThread t = new InterruptedThread();
    t.start();

    try {
      Thread.sleep(3000);
      //只是更改了一个标志位而已，并没有中断线程
      t.interrupt();
    } catch (InterruptedException e) {

    }

  }
}
