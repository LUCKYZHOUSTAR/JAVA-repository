package com.lucky.task.core.net.client;

import com.lucky.task.client.data.ClientInfo;
import com.lucky.task.client.data.ExecuteParam;
import com.lucky.task.client.data.Result;
import com.lucky.task.core.serialize.HessianSerializer;
import com.lucky.task.core.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JettyClient {
    private static Logger logger = LoggerFactory.getLogger(JettyClient.class);




    public static  Result send(ClientInfo clientInfo,String address){
        try {
            // serialize request
            byte[] requestBytes = HessianSerializer.serialize(clientInfo);

            // remote invoke
            byte[] responseBytes = HttpClientUtil.postRequest(address, requestBytes);
            if (responseBytes == null || responseBytes.length == 0) {
                Result rpcResponse = new Result(false, "RpcResponse byte[] is null");
                return rpcResponse;
            }

            // deserialize response
            Result rpcResponse = (Result) HessianSerializer.deserialize(responseBytes, Result.class);
            return rpcResponse;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Result rpcResponse = new Result(false, "Client-error:" + e.getMessage());
            return rpcResponse;
        }
    }
    public static Result send(ExecuteParam request, String address) throws Exception {
        try {
            // serialize request
            byte[] requestBytes = HessianSerializer.serialize(request);

            // remote invoke
            byte[] responseBytes = HttpClientUtil.postRequest(address, requestBytes);
            if (responseBytes == null || responseBytes.length == 0) {
                Result rpcResponse = new Result(false, "RpcResponse byte[] is null");
                return rpcResponse;
            }

            // deserialize response
            Result rpcResponse = (Result) HessianSerializer.deserialize(responseBytes, Result.class);
            return rpcResponse;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Result rpcResponse = new Result(false, "Client-error:" + e.getMessage());
            return rpcResponse;
        }
    }

}
