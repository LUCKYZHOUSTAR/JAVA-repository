package com.lucky.task.web.core;

import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.processor.ConsumerProcessor;
import com.lucky.task.core.serialize.SerializerFactory;
import com.lucky.task.core.util.HttpClientUtil;
import com.lucky.task.web.schedule.DefaultProviderProcessor;
import com.lucky.task.web.schedule.ScheduleFactory;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 13:29 2017/12/15
 */
@Service
public class HandlerService {


    public void addJob(String name, String group, String cron) throws SchedulerException {
        //保存jobin信息
        //添加job信息
        ScheduleFactory.addJob(name, group, cron);
    }

    private ConsumerProcessor consumerProcessor = new DefaultProviderProcessor();

    public void handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Response rpcResponse = null;
        byte[] requestBytes = HttpClientUtil.readBytes(request);
        if (requestBytes == null || requestBytes.length == 0) {
            rpcResponse = new Response(false, "RpcRequest byte[] is null");
            consumerProcessor.handlerFailed(rpcResponse, response);
            return;
        }
        RpcRequest rpcRpcRequest = SerializerFactory.getSerializer().readObject(requestBytes, RpcRequest.class);
        consumerProcessor.handleRequest(rpcRpcRequest, response);
    }
}
