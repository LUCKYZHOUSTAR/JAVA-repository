package cmc.lucky.test;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午9:47 2018/10/30
 */
public class TestA

{
  // 创建两把锁对象
  public static final Object objA = new Object();
  public static final Object objB = new Object();


  /**
   * 测死代码
   */
  public static void main(String[] args) {
    DieLock dl1 = new DieLock(true);
    DieLock dl2 = new DieLock(false);
    dl1.start();
    dl2.start();
  }


  public static class DieLock extends Thread {

    // 设置一个旗标,控制线程的执行与停止
    private Boolean flag;

    public DieLock(Boolean flag) {
      this.flag = flag;
    }

    @Override
    public void run() {
      if (flag) {
        synchronized (TestA.objA) {
          System.out.println("if A");
          synchronized (TestA.objB) {
            System.out.println("if B");
          }
        }
      } else {
        synchronized (TestA.objB) {
          System.out.println("else B");
          synchronized (TestA.objA) {
            System.out.println("else A");
          }
        }
      }
    }
  }


}
