package com.test;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午6:17 2018/8/1
 */
public class Test2 {


  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }



  /*
  传值引用
   */
  public static void main(String[] args) {
    User user=new User();
    user.setId("23");
    user.setName("w334");
    Test1 test1 = new Test1();
    test1.setUser(user);

    Test2 test2 = new Test2();
    test2.setUser(user);


    test1.getUser().setName("王八");
    System.out.println(test2.getUser().getName());
  }
}
