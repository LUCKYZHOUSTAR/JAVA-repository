package reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午3:07 2018/6/26
 */
public class ReflectTest {


  public static void main(String[] args) {
    System.out.println(getParameterNameJava8(User.class,"test"));
  }
  public static List<String> getParameterNameJava8(Class clazz, String methodName){
    List<String> paramterList = new ArrayList<>();
    Method[] methods = clazz.getDeclaredMethods();
    for (Method method : methods) {
      if (methodName.equals(method.getName())) {
        Parameter[] params = method.getParameters();
        for(Parameter parameter : params){
          paramterList.add(parameter.getName());
        }

      }
    }

    return paramterList;
  }




}
