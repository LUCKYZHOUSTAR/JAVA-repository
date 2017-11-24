package cmc.lucky.serializable;

import java.io.Serializable;


/**
 * 也就是，需要传输的时候，才需要继承序列化的接口
 */

/**
 * @Author:chaoqiang.zhou
 * @Description:/*在写入的文件当中，会保存着写入对象的类的信息，我们可以打开看一下。 序列化只能存储在堆里的数据，不能存储在方法区的数据
 * 没有方法的接口，称之为标记接口，告诉别人，我具备序列化的资格，相当于盖章作用
 * 序列化运行时使用一个称为 serialVersionUID 的版本号与每个可序列化类相关联，用于给编译器做标识
 * 其数字签名实际上是根据其成员变量的name id（包含其对应的属性、类型 private String int）
 * 该接口给类添加了serialVersionUID，假设类A 序列号为12L 产生了一个对象a,a被写入了磁盘当中，这时候，类A被修改，当被修改
 * 之后，类A的序列号也会对应发生改变
 * 该序列号在反序列化过程中用于验证序列化对象的发送者和接收者是否为该对象加载了与序列化兼容的类。
 * 如果接收者加载的该对象的类的 serialVersionUID 与对应的发送者的类的版本号不同，
 * 则反序列化将会导致 InvalidClassException。可序列化类可以通过声明名为 "serialVersionUID" 的字段（该字段必须是静态 (static)、最终 (final) 的 long 型字段）
 * 显式声明其自己的 serialVersionUID：
 * 需要注意的地方是，serialVersionUID不是决定由哪个类加载硬盘文件中的唯一因素，类的包名、类的名称都有关联。如果不一致，也会出现类型转化错误。原因是类的包名，类名已经被写入了文件当中。
 * *
 */
public class Student implements Serializable {

    /**
     * 那么其值是和类中的成员变量相关联的,利用成员变量自动生成的
     */
//    private static final long serialVersionUID = -5182532647273106745L;
    //static类型的数据存在于方法区中，不能被持久化。
    public static String countryName = "china";
    private int id;
    private String name;
    private String sex;

    //transient可以排序该字段进行序列化，解除了与成员变量的关系
    private transient String school;

    public String getSex() {

        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        String s = "adaf";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
