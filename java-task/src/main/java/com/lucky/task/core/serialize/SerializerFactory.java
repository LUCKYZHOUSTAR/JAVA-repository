package com.lucky.task.core.serialize;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:46 2017/12/14
 */
public class SerializerFactory {


    //后续可以抽象成接口
    public static KryoSerializer getSerializer() {

        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        //静态初始化器，由JVM来保证线程安全
        private static KryoSerializer instance = new KryoSerializer();
    }
}
