package basic.lambda;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:50 2018/10/26
 */

@FunctionalInterface
public interface MyLamda {


  public void test1(String y);


  //再定义一个会报错
//  public void test3();
  default String test2() {
    return "123";
  }

  default String test3() {
    return "123";
  }

  //static方法也可以定义
  static void test4() {
    System.out.println("234");
  }


  public static void main(String[] args) {
    MyLamda m = y -> System.out.println("w223");
  }
}
