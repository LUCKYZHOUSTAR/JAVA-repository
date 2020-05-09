package parent;

/**
 * @description:
 * @author: lucky
 * @created: 2020/05/07 11:04
 */
public class Child extends  User {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void eat() {

        System.out.println("孩子吃东西");
    }

    public static void main(String[] args) {
        Child child = new Child();

        child.setId("@323");
        User user = child;

        System.out.println(user);

        Child child1 = (Child)user;

        System.out.println(child1.getId());

        child.eat();
    }
}
