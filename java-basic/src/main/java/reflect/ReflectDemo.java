package reflect;

import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午3:33 2018/9/30
 */
public class ReflectDemo implements Serializable {

  public static void main(String[] args) throws Exception {

    Class<?> clazz = null;

    //获取Class对象的引用
    clazz = Class.forName("reflect.UserTest");

    //第一种方法，实例化默认构造方法，UserTest必须无参构造函数,否则将抛异常
    UserTest user = (UserTest) clazz.newInstance();
    user.setAge(20);
    user.setName("Rollen");
    System.out.println(user);

    System.out.println("--------------------------------------------");

    //获取带String参数的public构造函数
    Constructor cs1 = clazz.getConstructor(String.class);
    //创建UserTest
    UserTest user1 = (UserTest) cs1.newInstance("xiaolong");
    user1.setAge(22);
    System.out.println("user1:" + user1.toString());

    System.out.println("--------------------------------------------");

    //取得指定带int和String参数构造函数,该方法是私有构造private
    Constructor cs2 = clazz.getDeclaredConstructor(int.class, String.class);
    //由于是private必须设置可访问
    cs2.setAccessible(true);
    //创建user对象
    UserTest user2 = (UserTest) cs2.newInstance(25, "lidakang");
    System.out.println("user2:" + user2.toString());

    System.out.println("--------------------------------------------");

    //获取所有构造包含private
    Constructor<?> cons[] = clazz.getDeclaredConstructors();
    // 查看每个构造方法需要的参数
    for (int i = 0; i < cons.length; i++) {
      //获取构造函数参数类型
      Class<?> clazzs[] = cons[i].getParameterTypes();
      System.out.println("构造函数[" + i + "]:" + cons[i].toString());
      System.out.print("参数类型[" + i + "]:(");
      for (int j = 0; j < clazzs.length; j++) {
        if (j == clazzs.length - 1) {
          System.out.print(clazzs[j].getName());
        } else {
          System.out.print(clazzs[j].getName() + ",");
        }
      }
      System.out.println(")");
    }
  }

  public  static class UserTest {

    private int age;
    private String name;

    public UserTest() {
      super();
    }

    public UserTest(String name) {
      super();
      this.name = name;
    }

    /**
     * 私有构造
     */
    private UserTest(int age, String name) {
      super();
      this.age = age;
      this.name = name;
    }

    //..........省略set 和 get方法


    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }



}




