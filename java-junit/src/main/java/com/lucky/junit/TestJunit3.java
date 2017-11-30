package com.lucky.junit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:54 2017/11/30
 */
import junit.framework.AssertionFailedError;
import junit.framework.TestResult;
import org.junit.Test;

public class TestJunit3 extends TestResult {
    // add the error
    public synchronized void addError(Test test, Throwable t) {
        super.addError((junit.framework.Test) test, t);
    }

    // add the failure
    public synchronized void addFailure(Test test, AssertionFailedError t) {
        super.addFailure((junit.framework.Test) test, t);
    }
    @Test
    public void testAdd() {
        // add any test
    }

    // Marks that the test run should stop.
    public synchronized void stop() {
        //stop the test here
    }
}