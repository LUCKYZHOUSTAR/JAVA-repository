package com.lucky.util;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:52 2017/12/8
 */
public class ObjSize {

    public static void main(String[] args) throws Exception {
        out.println(VM.current().details());
        out.println(ClassLayout.parseClass(A.class).toPrintable());
    }

    public static class A {
        boolean f;
        public long p1;
        public volatile long value = 0L;

    }
}
