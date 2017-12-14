package com.lucky.task.core.processor;

import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.serialize.SerializerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:36 2017/12/14
 */
public interface ConsumerProcessor {
    void handleRequest(RpcRequest rpcRpcRequest, HttpServletResponse response) throws IOException;
    default void handlerFailed(Response rpcResponse, HttpServletResponse response) throws IOException {
        byte[] responseBytes = SerializerFactory.getSerializer().writeObject(rpcResponse);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }

}
