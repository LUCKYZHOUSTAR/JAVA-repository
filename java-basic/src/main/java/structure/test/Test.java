package structure.test;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午2:42 2018/10/9
 */
public class Test {


  private User user;


  public Test(User user) {
    this.user = user;
  }

  public void copy(){

    User user = this.user;
    user.setUserName("哈哈");
    System.out.println(this.user.getUserName());
    user = new User("sfd", "asfd");

  }


  public static void main(String[] args) {
    User user = new User("1", "3");
    Test test = new Test(user);
    System.out.println(test.user.getUserName());

    test.copy();

    System.out.println(test.user.getUserName());
  }







  public static class User
  {

    private String userName;

    private String code;


    public User(String userName, String code) {

      this.userName = userName;
      this.code = code;
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }
}

