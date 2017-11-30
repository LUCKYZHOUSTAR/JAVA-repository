package com.lucky.junit.exception;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:01 2017/11/30
 */
public class TestJunit {
    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test(expected = ArithmeticException.class)
    public void testPrintMessage() {
        System.out.println("Inside testPrintMessage()");
        messageUtil.printMessage();
    }

    @Test
    public void testSalutationMessage() {
        System.out.println("Inside testSalutationMessage()");
        message = "Hi!" + "Robert";
        assertEquals(message,messageUtil.salutationMessage());
    }
}
