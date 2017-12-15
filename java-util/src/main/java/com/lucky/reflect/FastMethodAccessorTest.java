package com.lucky.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:20 2017/12/15
 */
public class FastMethodAccessorTest {


    public static void main(String[] args) {

        //33 205 161 195 164
//        asmTest();
        fastMethod();
    }


    public static void asmTest() {
        SomeClass someObject = new SomeClass();
        MethodAccess access = MethodAccess.get(SomeClass.class);
        int fooIndex = access.getIndex("sayHello", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                access.invoke(someObject, fooIndex, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }


    public static void fastMethod() {
        SomeClass someObject = new SomeClass();
        FastMethodAccessor fastMethodAccessor = FastMethodAccessor.get(SomeClass.class);
        int fooIndex = fastMethodAccessor.getIndex("sayHello",String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                fastMethodAccessor.invoke(someObject, fooIndex, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }
}
