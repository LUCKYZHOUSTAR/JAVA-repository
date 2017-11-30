package com.lucky.junit;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:junit中的断言的用法
 * @Date:Create in 14:49 2017/11/30
 */
public class TestJunit1 {
    @Test
    public void testAdd() {
        //test data
        int num = 5;
        String temp = null;
        String str = "Junit is working fine";

        //check for equality
        assertEquals("Junit is working fine", str);

        //check for false condition
        assertFalse(num > 6);

        //check for not null value
        assertNotNull(str);
    }
}
