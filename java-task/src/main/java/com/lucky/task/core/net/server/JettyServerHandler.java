package com.lucky.task.core.net.server;

import com.lucky.task.client.data.ExecuteParam;
import com.lucky.task.client.data.Result;
import com.lucky.task.client.service.ExecutorFactory;
import com.lucky.task.core.serialize.HessianSerializer;
import com.lucky.task.core.util.HttpClientUtil;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


/**
 * invoke
 */
public class JettyServerHandler extends AbstractHandler {
    private static Logger logger = LoggerFactory.getLogger(JettyServerHandler.class);

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // invoke
        Result rpcResponse = doInvoke(request);
        // serialize response
        byte[] responseBytes = HessianSerializer.serialize(rpcResponse);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }

    private Result doInvoke(HttpServletRequest request) {
        try {
            // deserialize request
            byte[] requestBytes = HttpClientUtil.readBytes(request);
            if (requestBytes == null || requestBytes.length == 0) {
                Result rpcResponse = new Result(false, "RpcRequest byte[] is null");
                return rpcResponse;
            }
            ExecuteParam rpcRequest = (ExecuteParam) HessianSerializer.deserialize(requestBytes, ExecuteParam.class);

            // invokel
            Result rpcResponse = ExecutorFactory.getService(rpcRequest.getName()).execute(rpcRequest);
            return rpcResponse;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Result rpcResponse = new Result(false, "Server-error:" + e.getMessage());
            return rpcResponse;
        }
    }

}
