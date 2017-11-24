package cmc.lucky.basic.transportion.server.impl;


import cmc.lucky.basic.transportion.server.netty.NettyRemotingServer;
import cmc.lucky.basic.transportion.server.netty.NettyRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:39 2017/10/26
 */
public class DefaultNettyServer {

    private Logger logger = LoggerFactory
            .getLogger(DefaultNettyServer.class);

    private int accepts = 3000;
    private int corePoolSize = 1;
    private int maximumPoolSize = 5;
    private long keepAliveTime = 1000L;
    private int port;
    private String remoteAddr;
    private NettyRequestProcessor processor;


    public DefaultNettyServer(int port, String remoteAddr, NettyRequestProcessor processor) {
        this.port = port;
        this.remoteAddr = remoteAddr;
        this.processor = processor;
    }


    public void start() {
        this.init();
    }

    private void init() {

        BlockingQueue workQueue = new LinkedBlockingQueue(this.accepts);
        ExecutorService executor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize,
                this.keepAliveTime, TimeUnit.MILLISECONDS, workQueue);
        NettyRemotingServer nserver = new NettyRemotingServer(this.port, this.processor, executor, this.remoteAddr);
        nserver.start();
    }


    public void setAccepts(int accepts) {
        this.accepts = accepts;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProcessor(NettyRequestProcessor processor) {
        this.processor = processor;
    }

}
