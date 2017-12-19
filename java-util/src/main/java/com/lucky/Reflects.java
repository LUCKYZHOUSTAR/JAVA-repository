package com.lucky;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 19:26 2017/12/19
 */
public class Reflects {
    private static final Objenesis objenesis = new ObjenesisStd(true);


    //没有调用构造函数
    public Object newInstance(Class<?> clazz) {
        return objenesis.newInstance(clazz);

    }
}
