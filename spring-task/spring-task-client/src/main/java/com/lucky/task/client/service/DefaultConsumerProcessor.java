package com.lucky.task.client.service;

import com.lucky.task.core.config.ExecuteParam;
import com.lucky.task.core.enums.ProcessType;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.processor.ConsumerProcessor;
import com.lucky.task.core.serialize.SerializerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:39 2017/12/14
 */
public class DefaultConsumerProcessor implements ConsumerProcessor {
    @Override
    public void handleRequest(RpcRequest rpcRpcRequest, HttpServletResponse response) throws IOException {
        //处理执行的请求信息
        if (rpcRpcRequest.getType() == ProcessType.request.value()) {
            serverHandler(rpcRpcRequest, response);
        } else {
            Response rpcResponse = new Response(false, "process type is not supported");
            handlerFailed(rpcResponse, response);
        }
    }


    public void serverHandler(RpcRequest rpcRpcRequest, HttpServletResponse response) throws IOException {

        //处理执行的请求信息
        if (rpcRpcRequest.getType() != ProcessType.request.value()) {
            return;
        }
        ExecuteParam param = (ExecuteParam) rpcRpcRequest.getBody();
        Response rpcResponse = ExecutorFactory.getService(param.getName()).execute(param);
        byte[] responseBytes = SerializerFactory.getSerializer().writeObject(rpcResponse);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }
}
