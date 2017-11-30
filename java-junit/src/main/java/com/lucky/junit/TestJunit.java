package com.lucky.junit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:46 2017/11/30
 */
public class TestJunit {
    String message = "Hello World";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testPrintMessage() {
        assertEquals(message, messageUtil.printMessage());
    }


    @Test
    public void testPrintMessage2() {
        assertEquals("dd", messageUtil.printMessage());
    }
}
