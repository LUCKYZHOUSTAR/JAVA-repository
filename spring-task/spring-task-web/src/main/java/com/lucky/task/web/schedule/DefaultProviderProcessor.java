package com.lucky.task.web.schedule;

import com.alibaba.fastjson.JSON;
import com.lucky.task.core.config.ServerOptions;
import com.lucky.task.core.enums.ProcessType;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.processor.ConsumerProcessor;
import com.lucky.task.core.serialize.SerializerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author:chaoqiang.zhou
 * @Description:默认的实现方式
 * @Date:Create in 12:38 2017/12/15
 */
public class DefaultProviderProcessor implements ConsumerProcessor {
    @Override
    public void handleRequest(RpcRequest rpcRpcRequest, HttpServletResponse response) throws IOException {
        //处理上报的请求
        if (rpcRpcRequest.getType() == ProcessType.report.value()) {
            reportHandler(rpcRpcRequest, response);
        } else {
            Response rpcResponse = new Response(false, "process type is not supported");
            handlerFailed(rpcResponse, response);
        }
    }

    public void reportHandler(RpcRequest rpcRpcRequest, HttpServletResponse response) throws IOException {

        //处理执行的请求信息
        if (rpcRpcRequest.getType() != ProcessType.report.value()) {
            return;
        }

        ServerOptions param = (ServerOptions) rpcRpcRequest.getBody();
        if(param.getStatus().value()== ServerOptions.Status.Start.value()){
            System.out.println("获取到了上报的信息{}" + JSON.toJSONString(param));
            Set<ServerOptions> options = new HashSet<>();
            options.add(param);
            JobInfoFactory.jobs.putIfAbsent(param.getName(), options);
        }else{
            System.out.println("移除的无用的bean嘻嘻你");
        }


        Response rpcResponse = new Response(true, "report");
        byte[] responseBytes = SerializerFactory.getSerializer().writeObject(rpcResponse);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }
}
