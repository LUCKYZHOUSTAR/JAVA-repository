package com.lucky.task.core.net.server;

import com.alibaba.fastjson.JSON;
import com.lucky.task.client.data.ExecuteParam;
import com.lucky.task.client.service.ExecutorFactory;
import com.lucky.task.core.config.ServerOptions;
import com.lucky.task.core.enums.ProcessType;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.serialize.SerializerFactory;
import com.lucky.task.core.util.HttpClientUtil;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 客户端的server，用来接收执行任务的通知
 */
public class ServerHandler extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Response rpcResponse = null;

        byte[] requestBytes = HttpClientUtil.readBytes(request);
        if (requestBytes == null || requestBytes.length == 0) {
            rpcResponse = new Response(false, "RpcRequest byte[] is null");
            handlerFailed(rpcResponse, response);
            baseRequest.setHandled(true);
            return;
        }


        RpcRequest rpcRpcRequest =  SerializerFactory.getSerializer().readObject(requestBytes, RpcRequest.class);
        //处理执行的请求信息
        if (rpcRpcRequest.getType() == ProcessType.request.value()) {
            serverHandler(rpcRpcRequest, response);
        } else if (rpcRpcRequest.getType() == ProcessType.report.value()) {
            reportHandler(rpcRpcRequest, response);
        } else {
            rpcResponse = new Response(false, "process type is not supported");
            handlerFailed(rpcResponse, response);
        }


    }

    private void handlerFailed(Response rpcResponse, HttpServletResponse response) throws IOException, ServletException {
        byte[] responseBytes = SerializerFactory.getSerializer().writeObject(rpcResponse);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
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

    public void reportHandler(RpcRequest rpcRpcRequest, HttpServletResponse response) throws IOException {

        //处理执行的请求信息
        if (rpcRpcRequest.getType() != ProcessType.report.value()) {
            return;
        }

        //todo：开始处理上报的请求信息
        ServerOptions param = (ServerOptions) rpcRpcRequest.getBody();
        System.out.println("获取到了上报的信息{}" + JSON.toJSONString(param));
        Response rpcResponse=new Response(true,"report");
        byte[] responseBytes =SerializerFactory.getSerializer().writeObject(rpcResponse);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }

}
