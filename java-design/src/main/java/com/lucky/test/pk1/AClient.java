package com.lucky.test.pk1;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/11 11:03
 * @Description:
 */
//当Aclient，想用fly，fly1的时候，直接依赖接口即可
public class AClient {

    private FlyService2 flyService2=new FlyServiceImpl1();


    public static void main(String[] args) {
        AClient aClient = new AClient();

        aClient.flyService2.fly3("234");
    }


}
