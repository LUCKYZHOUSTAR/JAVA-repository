package com.lucky.task.core.net.interceptor;

import com.lucky.task.client.data.ClientInfo;
import com.lucky.task.client.data.Result;
import com.lucky.task.core.net.client.JettyClient;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:56 2017/12/13
 */
public class ServerStartInterceptorImpl implements ServerStartInterceptor {

    private ClientInfo clientInfo;

    @Override
    public void BeforedoSomeThing() throws Exception {
        Result result = JettyClient.send(clientInfo, "");
        if (result == null || !result.isSuccess()) {
            throw new Exception("上报该client信息到注册中心报错");
        }
    }

    @Override
    public void AfterdoSomeThing() throws Exception {
        Result result = JettyClient.send(clientInfo, "");
        if (result == null || !result.isSuccess()) {
            throw new Exception("注销该client信息到注册中心报错");
        }
    }
}
