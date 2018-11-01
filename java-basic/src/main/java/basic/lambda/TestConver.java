package basic.lambda;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午12:41 2018/10/26
 */
@FunctionalInterface
public interface TestConver<T, F> {


  F convert(T t);

  
  public static void main(String[] args) {
    TestConver<String, Integer> t = Integer::valueOf;
    Integer e = t.convert("23");
  }
}

