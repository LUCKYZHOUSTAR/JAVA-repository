package basic.exception;

import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:58 2017/12/20
 */
public class ExceptionTest {
    public void doSomething() throws NullPointerException {
        System.out.println();
    }
    public static void main(){
        ExceptionTest ett = new ExceptionTest();
        ett.doSomething();
    }
}
