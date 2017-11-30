package com.lucky.junit.timeout;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestJunit {

    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test(timeout = 1000)
    public void testPrintMessage() {
        //用来验证该方法执行时间是否超时
        System.out.println("Inside testPrintMessage()");
        messageUtil.printMessage();
    }

    @Test
    public void testSalutationMessage() {
        System.out.println("Inside testSalutationMessage()");
        message = "Hi!" + "Robert";
        assertEquals(message, messageUtil.salutationMessage());
    }
}