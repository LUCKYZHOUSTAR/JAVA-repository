package basic.function;

import org.junit.Test;

import java.util.function.Function;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:09 2017/11/17
 */
@FunctionalInterface
public interface Converter<F, T> {

    T convert(F from);


    public static void main(String[] args) {
        //方法中有几个参数就传递几个参数
        //如果一个函数参数接收一个单方法的接口而你传递的是一个function，Rhino 解释器会自动做一个单接口的实例到function的适配器，典型的应用场景有 org.w3c.dom.events.EventTarget 的addEventListener 第二个参数 EventListener。
//        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        //静态方法引用
        Converter<String, Integer> converter = Integer::valueOf;

        Function<String,Integer> function= Integer::valueOf;
        System.out.println(function.apply("33"));

        Function<String,Integer> function1=(a)->{
          return 2;
        };
        Integer converted = converter.convert("123");
        System.out.println(converted);   // 123

    }





}
