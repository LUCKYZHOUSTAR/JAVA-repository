package basic.lock;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:44 2018/1/19
 */
public class FinalTest {

    public static final String LOAN = "loan";

    public static void main(String[] args) {
        //基本类似，值是不可以变的
//        LOAN = new String("loan") //invalid compilation error
    }



    public void test1(){
        final PersonalLoan personalLoan=new PersonalLoan();
//        personalLoan=new PersonalLoan();//一旦修饰就不能在指向其他变量了
    }
    /**
     * 什么是final方法?
     * final也可以声明方法。方法前面加上final关键字，代表这个方法不可以被子类的方法重写。如果你认为一个方法的功能已经足够完整了，子类中不需要改变的话，你可以声明此方法为final。final方法比非final方法要快，因为在编译的时候已经静态绑定了，不需要在运行时再动态绑定。下面是final方法的例子：
     */
    class PersonalLoan {
        public final String getName() {
            return "personal loan";
        }
    }

    class CheapPersonalLoan extends PersonalLoan {
//        @Override
//        public final String getName() {
//            return "cheap personal loan"; //compilation error: overridden method is final
//        }
    }

    /**
     * 什么是final类？

     使用final来修饰的类叫作final类。final类通常功能是完整的，
     它们不能被继承。Java中有许多类是final的，譬如String, Interger以及其他包装类。下面是final类的实例：
     */
}
