package cmc.netty.util;

import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:http://ifeve.com/using-as-a-generic-library/
 * @Date:Create in 18:51 2018/1/18
 */
public class FooTest extends DefaultAttributeMap {
    public static final AttributeKey<String> ATTR_A = AttributeKey.valueOf("A");

    public static final AttributeKey<Integer> ATTR_B = AttributeKey.valueOf("B");


    public static void main(String[] args) {
        FooTest o = new FooTest();
        o.attr(ATTR_A).set("foo");
    }
}
