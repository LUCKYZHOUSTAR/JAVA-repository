package basic.jvm;

import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description: java中class.forName和classLoader都可用来对类进行加载。前者除了将类的.class文件加载到jvm中之外，
 * 还会对类进行解释，执行类中的static块。而classLoader只干一件事情，就是将.class文件加载到jvm中，
 * 不会执行static中的内容,只有在newInstance才会去执行static块。Class.forName(name, initialize, loader)带参函数也可控制是否加载static块。
 * 并且只有调用了newInstance()方法采用调用构造函数，创建类的对象
 * @Date:Create in 18:56 2017/12/19
 */
public class ClassUtils {


    /**
     * 因此可以通过class.forname进行预先加载一些代码中的静态代码块操作
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        System.out.println("before loadClass... ");
        Class c = ClassUtils.class.getClassLoader().loadClass("basic.jvm.ClassInfo");
        System.out.println("after loadClass... ");
        System.out.println("before newInstance... ");
        ClassInfo info1 = (ClassInfo) c.newInstance();
        System.out.println("after newInstance... ");
    }


    @Test
    public void test2() throws Exception {
        System.out.println("before class.forName");
        Class cc = Class.forName("basic.jvm.ClassInfo");
        System.out.println("after class.forName");
        ClassInfo info2 = (ClassInfo) cc.newInstance();

        ClassInfo info3 = (ClassInfo) cc.newInstance();

        ClassInfo classInfo = new ClassInfo();
    }
}
