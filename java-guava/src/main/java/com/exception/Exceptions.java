package com.exception;

import com.google.common.base.Throwables;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:16 2017/12/20
 */
public class Exceptions {


    /**
     * 1. 将异常信息存入数据库、日志文件、或者邮件等。

     2. 将受检查的异常转换为运行时异常

     3. 在代码中得到引发异常的最低层异常

     4. 得到异常链

     5. 过滤异常，只抛出感兴趣的异常

     而借助于Guava的Throwables，我们可以很方便的做到这些：

     以list的方式得到throwable的异常链：
     */


    /**
     *
     static List<Throwable> getCausalChain(Throwable throwable)


     返回最底层的异常

     1
     static Throwable getRootCause(Throwable throwable)


     返回printStackTrace的结果string


     1
     static String getStackTraceAsString(Throwable throwable)
     把受检查的异常转换为运行时异常：

     1
     public static RuntimeException propagate(Throwable throwable)
     只抛出感兴趣的异常：

     1
     2
     public static <X extends Throwable> void propagateIfInstanceOf(
     @Nullable Throwable throwable, Class<X> declaredType) throws X

     propagateIfPossible方法只会对RuntimeException或者Error异常感兴趣
     */






    @Test
    public void test1(){
        try {

            throw new NullPointerException();
        }catch (RuntimeException e){
            throw new NullPointerException();

        }catch (Exception e){
            Throwables.propagateIfInstanceOf(e,NullPointerException.class);
            throw Throwables.propagate(e);
        }
    }
}
