package com.basic;

import com.google.common.base.Throwables;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:　　有时候, 当我们我们捕获异常,
 * 并且像把这个异常传递到下一个try/catch块中。Guava提供了一个异常处理工具类, 可以简单地捕获和重新抛出多个异常。例如：
 * @Date:Create in 11:04 2017/11/17
 */
public class ThrowablesTest {

    /**
     * 　1.RuntimeException propagate(Throwable)：把throwable包装成RuntimeException，用该方法保证异常传递，抛出一个RuntimeException异常
     * 　　2.void propagateIfInstanceOf(Throwable, Class<X extends Exception>) throws X：当且仅当它是一个X的实例时，传递throwable
     * 　　3.void propagateIfPossible(Throwable)：当且仅当它是一个RuntimeException和Error时，传递throwable
     * 　　4.void propagateIfPossible(Throwable, Class<X extends Throwable>) throws X：当且仅当它是一个RuntimeException和Error时，或者是一个X的实例时，传递throwable。
     */
    @Test
    public void testThrowables() {
        try {
            throw new Exception();
        } catch (Exception t) {
            String ss = Throwables.getStackTraceAsString(t);
            System.out.println("ss:" + ss);
//            Throwables.propagate(t);
        }
    }

    @Test
    public void call() throws IOException {
        try {
            throw new IOException();
        } catch (Throwable t) {
//            Throwables.propagateIfInstanceOf(t, IOException.class);
            throw Throwables.propagate(t);
        }
    }


    @Test
    public void call2() throws IOException {
        try {
            throw new IOException();
        } catch (Throwable t) {
            Throwables.propagateIfInstanceOf(t, IOException.class);
            throw Throwables.propagate(t);
        }
    }


//    @Test
//    public void call3()throws IOException{
//        try {
//            throw new IOException();
//        } catch (Exception e) {
//        } catch (Throwable t) {
//            Throwables.propagateIfInstanceOf(t, IOException.class);
//            Throwables.propagateIfInstanceOf(t, SQLException.class);
//            throw Throwables.propagate(t);
//        }
//    }


    /**
     *
     1
     2
     3
     4
     5
     6
     7
     8
     9
     try {
     someMethodThatCouldThrowAnything();
     } catch (IKnowWhatToDoWithThisException e) {
     handle(e);
     } catch (Throwable t) {
     Throwables.propagateIfInstanceOf(t, IOException.class);
     Throwables.propagateIfInstanceOf(t, SQLException.class);
     throw Throwables.propagate(t);
     }
     　　上面的例子中，只抛出感兴趣的IOException或者SQLException，至于其他的异常直接转换为运行时异常。

     1
     2
     3
     4
     5
     6
     7
     8
     try {
     someMethodThatCouldThrowAnything();
     } catch (IKnowWhatToDoWithThisException e) {
     handle(e);
     } catch (Throwable t) {
     Throwables.propagateIfPossible(t);
     throw new RuntimeException("unexpected", t);
     }
     　　propagateIfPossible方法只会对RuntimeException或者Error异常感兴趣

     1
     2
     3
     4
     5
     6
     7
     8
     try {
     someMethodThatCouldThrowAnything();
     } catch (IKnowWhatToDoWithThisException e) {
     handle(e);
     } catch (Throwable t) {
     Throwables.propagateIfPossible(t, OtherException.class);
     throw new RuntimeException("unexpected", t);
     }
     　　propagateIfPossible的另外一个重载方法，容许我们来自己指定一个感兴趣的异常。这样这个方法只会对RuntimeException或者Error和我们指定的这3种异常感兴趣。

     1
     2
     3
     4
     5
     6
     7
     8
     9
     T doSomething() {
     try {
     return someMethodThatCouldThrowAnything();
     } catch (IKnowWhatToDoWithThisException e) {
     return handle(e);
     } catch (Throwable t) {
     throw Throwables.propagate(t);
     }
     }
     　　上面的方法会将受检查的异常转换为运行时异常。
     */
}
