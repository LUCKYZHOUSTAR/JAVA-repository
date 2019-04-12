package basic.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:01 2018/10/26
 */
public class HelloLambda {


  public void function(Callable callable) {

    Object result = null;

    try {
      result = callable.call();
      System.out.println(result.getClass().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    HelloLambda helloLambda = new HelloLambda();
    helloLambda.function(() -> new String("hello"));

  }


  @Test
  public void testList() {
    //初始化list集合
    List<String> list = new ArrayList<String>();
    list.add("测试数据1");
    list.add("测试数据2");
    list.add("测试数据3");
    list.add("测试数据12");

    //使用λ表达式遍历集合
    list.forEach(s -> System.out.println(s));

    Predicate<String> contain1 = n -> n.contains("1");
    Predicate<String> contain2 = n -> n.contains("2");

    list.stream().filter(contain1).forEach(n -> System.out.println(n));
    list.stream().filter(contain1.and(contain2)).forEach(n -> System.out.println(n));
    list.stream().filter(contain1.or(contain2)).forEach(n -> System.out.println(n));
    List<String> newList = list.stream().filter(contain1.and(contain2))
        .collect(Collectors.toList());
    newList.forEach(s -> System.out.println(s));

  }
}
