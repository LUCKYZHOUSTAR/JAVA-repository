package t;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlmapperGeneric<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }


    public void noSpecifyType() {
        GlmapperGeneric glmapperGeneric = new GlmapperGeneric();
        glmapperGeneric.setT("test");
        //需要强制类型转化
        String test = (String) glmapperGeneric.getT();

        System.out.println(test);
    }


    //    上面这段代码中的 specifyType 方法中 省去了强制转换，可以在编译时候检查类型安全，可以用在类，方法，接口上。
    public void specifyType() {
        GlmapperGeneric<String> glmapperGeneric = new GlmapperGeneric<>();
        glmapperGeneric.setT("test");
        //那么泛型的好处就是在编译的时候能够检查类型安全，并且所有的强制转换都是自动和隐式的。
        //不需要强制类型转化
        String test = glmapperGeneric.getT();
        System.out.println(test);
    }


    //    上界通配符 < ? extends E>
    //上届：用 extends 关键字声明，表示参数化的类型可能是所指定的类型，或者是此类型的子类。

    //    下界通配符 < ? super E>
//    下界: 用 super 进行声明，表示参数化的类型可能是所指定的类型，或者是此类型的父类型，直至 Object
    //通配符的作用
    static int countLegs(List<? extends Animal> animals) {
        int retVal = 0;
        for (Animal animal : animals) {
            retVal += animal.countLegs();
        }
        return retVal;
    }

    static int countLegs1(List<Animal> animals) {
        int retVal = 0;
        for (Animal animal : animals) {
            retVal += animal.countLegs();
        }
        return retVal;
    }

    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        // 不会报错
        countLegs(dogs);
        // 报错
//        countLegs1(dogs);
    }

    public static class Animal {

        public int countLegs() {

            return 0;
        }
    }


    public static class Dog extends Animal {

    }


    //方法泛型
    public class StateHelper {
        public <S, E, C> State<S, E, C> getState(Map<S, State<S, E, C>> stateMap, S stateId) {
            State<S, E, C> state = stateMap.get(stateId);
            return state;
        }
    }


    public interface State<S, E, C> {

    }
}
