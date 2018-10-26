package basic.lambda;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:48 2018/10/26
 */
public class NewCharacterImpl implements NewCharacter {

  @Override
  public void test1() {

  }


  //其实这么定义一个方法的主要意义是定义一个默认方法，也就是说这个接口的实现类实现了这个接口之后，不用管这个default修饰的方法，也可以直接调用，如下。
  public static void main(String[] args) {
    NewCharacter a = new NewCharacterImpl();
    a.test2();

  }
}
