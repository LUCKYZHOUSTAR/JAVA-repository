package com.lucky.task.core.net.client;

import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.serialize.SerializerFactory;
import com.lucky.task.core.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 发送处理的请求信息
 */
public class JettyClient {
    private static Logger logger = LoggerFactory.getLogger(JettyClient.class);

    public static Response send(RpcRequest rpcRequest, String address) {
        try {
            byte[] requestBytes = SerializerFactory.getSerializer().writeObject(rpcRequest);

            byte[] responseBytes = HttpClientUtil.postRequest(address, requestBytes);
            if (responseBytes == null || responseBytes.length == 0) {
                Response rpcResponse = new Response(false, "RpcResponse byte[] is null");
                return rpcResponse;
            }
            Response rpcResponse = (Response) SerializerFactory.getSerializer().readObject(responseBytes, Response.class);
            return rpcResponse;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Response rpcResponse = new Response(false, "Client-error:" + e.getMessage());
            return rpcResponse;
        }
    }
}
