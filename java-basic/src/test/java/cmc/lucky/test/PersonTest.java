package cmc.lucky.test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:11 2018/1/19
 */
public class PersonTest {

    String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void main(String[] args) {
        //java中的引用复制，牛逼了
        PersonTest personTest=new PersonTest();
        personTest.setId("2324");
        PersonTest P2=personTest;
        P2.setId("90");
        System.out.println(P2.getId());
        System.out.println(personTest.getId());
    }
}
