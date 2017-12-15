package com.lucky.task.core.net.server;

import com.alibaba.fastjson.JSON;
import com.lucky.task.core.config.ServerOptions;
import com.lucky.task.core.enums.ProcessType;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.processor.ConsumerProcessor;
import com.lucky.task.core.serialize.SerializerFactory;
import com.lucky.task.core.util.HttpClientUtil;
import lombok.Setter;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 客户端的server，用来接收执行任务的通知
 */
@Service
public class ServerHandler extends AbstractHandler {

    @Setter
    private ConsumerProcessor processor;

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Response rpcResponse = null;

        byte[] requestBytes = HttpClientUtil.readBytes(request);
        if (requestBytes == null || requestBytes.length == 0) {
            rpcResponse = new Response(false, "RpcRequest byte[] is null");
            processor.handlerFailed(rpcResponse, response);
            baseRequest.setHandled(true);
            return;
        }


        RpcRequest rpcRpcRequest = SerializerFactory.getSerializer().readObject(requestBytes, RpcRequest.class);
        processor.handleRequest(rpcRpcRequest, response);


    }




}
