package parent;

/**
 * @description:
 * @author: lucky
 * @created: 2020/05/07 11:04
 */
public abstract class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println("父亲吃东西");
    }

    public void process() {
        eat();

    }

}
