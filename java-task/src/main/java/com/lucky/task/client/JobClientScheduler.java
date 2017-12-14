package com.lucky.task.client;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.lucky.task.core.config.ServerOptions;
import com.lucky.task.core.enums.ProcessType;
import com.lucky.task.core.net.client.JettyClient;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.server.JettyServer;
import com.lucky.task.core.net.server.ServerHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerCollection;

/**
 * @Author:chaoqiang.zhou
 * @Description:客户端维护类信息
 * @Date:Create in 10:34 2017/12/14
 */
@Slf4j
public class JobClientScheduler {

    private JettyServer jettyServer;
    private ServerOptions serverOptions;

    public JobClientScheduler(ServerOptions serverOptions) {
        Preconditions.checkNotNull(serverOptions);
        Preconditions.checkNotNull(serverOptions.getRegisterCenterAdd());
        Preconditions.checkNotNull(serverOptions.getIp());
        Preconditions.checkNotNull(serverOptions.getPort());
        this.serverOptions = serverOptions;
        HandlerCollection handlerc = new HandlerCollection();
        handlerc.setHandlers(new Handler[]{new ServerHandler()});
        jettyServer = new JettyServer();
        jettyServer.setHandlerCollection(handlerc);
    }

    public void start() throws Exception {
        log.info("start to jetty server{}", JSON.toJSONString(serverOptions));
        jettyServer.start(serverOptions.getPort());
        //该执行器启动的信息上报到注册中心去
        report(serverOptions);
    }


    /**
     * 销毁方法，因为销毁的时候需要手动关闭jetty的端口信息
     */
    public void destroy() {
        //手动关闭jetty端口信息
        jettyServer.destroy();
        serverOptions.setStatus(ServerOptions.Status.Destory);
        destroyReport(serverOptions);
    }


    /**
     * 上报执行器信息
     */
    private void report(ServerOptions serverOptions) throws Exception {
        log.info("task client{} start success，report to registry center", JSON.toJSONString(serverOptions));
        Response response = JettyClient.send(buildRequest(serverOptions, ProcessType.report.value()), serverOptions.getRegisterCenterAdd());
        if (response == null || !response.isSuccess()) {
            log.info("task client{} start success，failed to report to registry center", JSON.toJSONString(serverOptions));
            throw new Exception("report failed");
        }
        log.info("task client{} start success，success to report to registry center", JSON.toJSONString(serverOptions));

    }

    private void destroyReport(ServerOptions serverOptions) {
        log.info("task client{} destroy success，report to registry center", JSON.toJSONString(serverOptions));
        Response response = JettyClient.send(buildRequest(serverOptions, ProcessType.report.value()), serverOptions.getRegisterCenterAdd());
        if (response == null || !response.isSuccess()) {
            log.info("task client{} start success，failed to report to registry center", JSON.toJSONString(serverOptions));
        }
        log.info("task client{} destroy success，success to report to registry center", JSON.toJSONString(serverOptions));

    }


    private RpcRequest buildRequest(ServerOptions serverOptions, int type) {
        RpcRequest rpcRequest = new RpcRequest(type, serverOptions);
        return rpcRequest;
    }

}
