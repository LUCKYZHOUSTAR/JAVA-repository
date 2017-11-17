package com.basic;

import com.google.common.base.Preconditions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:22 2017/11/17
 */
public class PreconditionsTest {
    //打印输出方法
    private static void print(Object obj) {
        System.out.println(String.valueOf(obj));
    }

    //测试方法
    private static boolean testMethod() {
        return 1 > 2;
    }

    //测试对象
    private static Object testObject() {
        return null;
    }


    @Test
    public void testPrecon() {

//        Preconditions.checkArgument(3 < 4);
//        Preconditions.checkArgument(3 > 4, "%s is wrong", "3<4");
//        Preconditions.checkState(3>4);
        Preconditions.checkNotNull(null);

    }

    @Test
    public void testPreconditions() {
        //checkArgument
        try {
            //校验表达式是否正确，并使用占位符输出错误信息
            Preconditions.checkArgument(1 > 2, "%s is wrong", "1 > 2");
        } catch (IllegalArgumentException e) {
            print(e.getMessage()); // 1 > 2 is wrong
        }
        //checkState
        try {
            //校验表达式是否正确，并使用占位符输出错误信息，使用方法作为表达式
            Preconditions.checkState(testMethod(), "%s is wrong", "testMethod()");
        } catch (IllegalStateException e) {
            print(e.getMessage()); // testMethod() is wrong
        }
        //checkNotNull
        try {
            //校验对象是否为空，并使用占位符输出错误信息
            Preconditions.checkNotNull(testObject(), "%s is null", "testObject()");
        } catch (NullPointerException e) {
            print(e.getMessage()); // testObject() is null
        }
        //初始化测试用list
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        //checkElementIndex
        try {
            //校验元素索引是否有效 ，使用checkPositionIndex校验
            Preconditions.checkElementIndex(10, list.size());
            //在临界值size处产生异常
        } catch (IndexOutOfBoundsException e) {
            print(e.getMessage()); // index (10) must be less than size (10)
        }
        //checkPositionIndex
        try {
            //校验元素索引是否有效，使用checkPositionIndex校验
            Preconditions.checkPositionIndex(10, list.size());
            //在临界size处不产生异常
            print("checkPositionIndex does not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            print(e.getMessage()); // checkPositionIndex does not throw IndexOutOfBoundsException
        }
        //checkPositionIndexes
        try {
            //校验是否是有效的索引区间
            Preconditions.checkPositionIndexes(3, 11, list.size());
        } catch (IndexOutOfBoundsException e) {
            print(e.getMessage()); // end index (11) must not be greater than size (10)
        }
    }
}
