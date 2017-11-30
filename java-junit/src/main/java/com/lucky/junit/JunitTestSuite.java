package com.lucky.junit;

import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * @Author:chaoqiang.zhou
 * @Description:可以用来包裹测试用例的信息
 * @Date:Create in 14:54 2017/11/30
 */
public class JunitTestSuite {
    public static void main(String[] a) {
        // add the test's in the suite
        TestSuite suite = new TestSuite(TestJunit1.class, TestJunit2.class, TestJunit3.class );
        TestResult result = new TestResult();
        suite.run(result);
        System.out.println("Number of test cases = " + result.runCount());
    }
}
