package com.lucky.task.admin;

import com.lucky.task.client.data.ExecuteParam;
import com.lucky.task.core.enums.ProcessType;
import com.lucky.task.core.net.client.JettyClient;
import com.lucky.task.core.net.codec.RpcRequest;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 13:21 2017/12/14
 */
public class ExecuteTask {


    public static void main(String[] args) throws Exception {
        ExecuteParam param = new ExecuteParam();
//        param.setName("");
//        param.setAddress();
//        Response response =JettyClient.send(param, "http://192.168.9.216:8080/");

//        System.out.println(response);

//        RpcRequest rpcRequest = new RpcRequest(ProcessType.report.value(), param);
//
//        byte[] requestBytes = SerializerFactory.getSerializer().writeObject(rpcRequest);
//        RpcRequest rpcResponse = SerializerFactory.getSerializer().readObject(requestBytes, RpcRequest.class);
//
//        System.out.println(rpcResponse);
//
//
//        Response response = new Response(false, "asdfasd");
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setName("ItemTask");
        RpcRequest rpcRequest1 = new RpcRequest(ProcessType.request.value(), executeParam);
        JettyClient.send(rpcRequest1, "http://192.168.9.216:8080/");

    }
}
