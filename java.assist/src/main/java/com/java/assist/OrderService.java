package com.java.assist;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/1/23 18:31
 * @Description:
 */
public class OrderService {


    public String test1() {
        System.out.println("我要执行了");

        try {

            System.out.println("我没有异常");
            throw new Exception();
        }catch (Exception e){

            System.out.println("我有异常了");

        }

        System.out.println("开始返回值了");
        return "sd";
    }
}
