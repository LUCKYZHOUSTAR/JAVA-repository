package basic.netty;

import basic.netty.client.Client;
import basic.netty.client.DefaultClient;
import basic.netty.data.Request;
import basic.netty.server.DefaultConsumereProcessor;
import basic.netty.server.Server;
import basic.netty.server.ServerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:33 2017/11/28
 */
public class ServerTest {


    private static Map<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        Server server = new ServerImpl(new DefaultConsumereProcessor());


        Client client = new DefaultClient(server);


        List<Request> requestList = new ArrayList();
        for (int i = 0; i < 100; i++) {
            Request request2 = new Request();
            request2.setBody("我是测试的" + i);
            requestList.add(request2);
        }

//        try {
        requestList.forEach(data -> {

            try {
                Request result1 = (Request) client.invoke(data);
                System.out.println(result1.getBody());
            } catch (Throwable e) {

            }

        });

//            for (int i = 0; i < 100; i++) {
//
//            }
//            Request result1 = (Request) client.invoke(request2);
//            System.out.println(result1);
//            System.out.println(InvokerResult.future.hashCode());
//            InvokerResult<Request> future = InvokerResult.future.remove(request2.getId());
//            Request result = future.getResult();
//            System.out.println(result.getBody());
//        } catch (Throwable e) {
//            System.out.println(e);
//        }
    }
}
