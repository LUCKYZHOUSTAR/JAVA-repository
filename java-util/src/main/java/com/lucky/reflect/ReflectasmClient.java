package com.lucky.reflect;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Method;

/**
 * @Author:chaoqiang.zhou
 * @Description:reflectasm大部分的时间花费在了get的方法上，因此一定要缓存起来
 * https://unmi.cc/java-reflectasm-bytecode-usage/
 * @Date:Create in 15:13 2017/12/15
 */
public class ReflectasmClient {
    public static void main(String[] args) throws Exception {
        testJdkReflect();
//        testReflectAsm();
    }

    public static void testJdkReflect() throws Exception {
        SomeClass someObject = new SomeClass();
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                Method method = SomeClass.class.getMethod("foo", String.class);
                method.invoke(someObject, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }

    public static void testReflectAsm() {
        SomeClass someObject = new SomeClass();
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                MethodAccess access = MethodAccess.get(SomeClass.class);
                access.invoke(someObject, "foo", "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }


    public void test1() {
        SomeClass someObject = null;
        FieldAccess access = FieldAccess.get(SomeClass.class);
        access.set(someObject, "name", "Awesome McLovin");
        String name = (String) access.get(someObject, "name");
    }

    public void test3() {
        ConstructorAccess<SomeClass> access = ConstructorAccess.get(SomeClass.class);
        SomeClass someObject = access.newInstance();
    }


    /**
     * 用索引会更加的快速
     */
    public static void testReflectAsm3() {
        SomeClass someObject = new SomeClass();
        MethodAccess access = MethodAccess.get(SomeClass.class);
        int fooIndex = access.getIndex("foo", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                access.invoke(someObject, fooIndex, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }
}
