package basic.array;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午2:20 2018/8/22
 */
public class TwoThreadWaitNotify {


  private int start = 1;

  private boolean flag = false;


  public static void main(String[] args) {
    TwoThreadWaitNotify twoThread = new TwoThreadWaitNotify();

    Thread t1 = new Thread(new OuNum(twoThread));
    t1.setName("A");


    Thread t2 = new Thread(new JiNum(twoThread));
    t2.setName("B");

    t1.start();
    t2.start();

  }
  private static class OuNum implements Runnable {

    private TwoThreadWaitNotify twoThreadWaitNotify;

    public OuNum(TwoThreadWaitNotify twoThreadWaitNotify) {
      this.twoThreadWaitNotify = twoThreadWaitNotify;
    }

    @Override
    public void run() {

      synchronized (TwoThreadWaitNotify.class) {
        //如果是true的的话
        if (twoThreadWaitNotify.flag) {
          System.out.println("偶数--" + twoThreadWaitNotify.start);
          twoThreadWaitNotify.flag = false;
          twoThreadWaitNotify.start++;
          TwoThreadWaitNotify.class.notify();
        } else {
          try {
            TwoThreadWaitNotify.class.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }



  /**
   * 奇数线程
   */
  public static class JiNum implements Runnable {
    private TwoThreadWaitNotify number;

    public JiNum(TwoThreadWaitNotify number) {
      this.number = number;
    }

    @Override
    public void run() {
      while (number.start <= 100) {
        synchronized (TwoThreadWaitNotify.class) {
          System.out.println("奇数线程抢到锁了");
          if (!number.flag) {
            System.out.println(Thread.currentThread().getName() + "+-+奇数" + number.start);
            number.start++;

            number.flag = true;

            TwoThreadWaitNotify.class.notify();
          }else {
            try {
              TwoThreadWaitNotify.class.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
}
