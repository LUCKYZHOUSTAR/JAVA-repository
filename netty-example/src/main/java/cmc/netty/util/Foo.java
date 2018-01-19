package cmc.netty.util;

import io.netty.util.AbstractConstant;
import io.netty.util.ConstantPool;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:49 2018/1/18
 */
public final class Foo extends AbstractConstant<Foo> {

    Foo(int id, String name) {
        super(id, name);

    }

}


final class MyConstants {


    private static final ConstantPool<Foo> pool = new ConstantPool<Foo>() {

        @Override

        protected Foo newConstant(int id, String name) {

            return new Foo(id, name);

        }

    };


    public static Foo valueOf(String name) {

        return pool.valueOf(name);

    }


    public static final Foo A = valueOf("A");

    public static final Foo B = valueOf("B");

}


final class YourConstants {

    public static final Foo C = MyConstants.valueOf("C");

    public static final Foo D = MyConstants.valueOf("D");

}
