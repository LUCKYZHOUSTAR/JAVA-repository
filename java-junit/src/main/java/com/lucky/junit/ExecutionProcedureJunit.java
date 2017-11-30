package com.lucky.junit;

import org.junit.*;


/**
 * beforeClass() 方法首先执行，并且只执行一次。
 * afterClass() 方法最后执行，并且只执行一次。
 * before() 方法针对每一个测试用例执行，但是是在执行测试用例之前。
 * after() 方法针对每一个测试用例执行，但是是在执行测试用例之后。
 * 在 before() 方法和 after() 方法之间，执行每一个测试用例。
 */
public class ExecutionProcedureJunit {

    //execute only once, in the starting
    @BeforeClass
    public static void beforeClass() {
        System.out.println("in before class");
    }

    //execute only once, in the end
    @AfterClass
    public static void afterClass() {
        System.out.println("in after class");
    }

    //execute for each test, before executing test
    @Before
    public void before() {
        System.out.println("in before");
    }

    //execute for each test, after executing test
    @After
    public void after() {
        System.out.println("in after");
    }

    //test case 1
    @Test
    public void testCase1() {
        System.out.println("in test case 1");
    }

    //test case 2
    @Test
    public void testCase2() {
        System.out.println("in test case 2");
    }


}