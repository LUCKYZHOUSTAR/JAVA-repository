package com.lucky.junit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:39 2017/11/30
 */
public class CalculatorTest {
    @Test
    public void testAdd() {
        Calculator c = new Calculator();
        int result = c.add(1, 2);
        Assert.assertEquals(result, 3);
    }

    @Test
    //测试 sub()方法
    public void testSub() {
        Calculator c = new Calculator();
        int result = c.sub(2, 1);
        Assert.assertEquals(result, 1);
    }

    public static void main(String[] args) {

        System.out.println(1<<31);
        System.out.println(1<<31);

        System.out.println(1>>31);
    }
}
