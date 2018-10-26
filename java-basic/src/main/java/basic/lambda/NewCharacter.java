package basic.lambda;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:47 2018/10/26
 */
public interface NewCharacter {


  public void test1();

  public default void test2() {
    System.out.println("我是新特性2");
  }
}
