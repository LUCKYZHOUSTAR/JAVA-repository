package basic.test;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2020/2/3 10:34
 * @Description:
 */
public class Children extends User {

    private String age;


    public static void main(String[] args) throws Exception {

        Children t= Children.class.getDeclaredConstructor().newInstance();
        t.setName("23");
        System.out.println(t.getName());
    }
}
