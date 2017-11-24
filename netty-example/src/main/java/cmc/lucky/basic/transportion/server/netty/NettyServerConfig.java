package cmc.lucky.basic.transportion.server.netty;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:15 2017/10/26
 */
public class NettyServerConfig {

    private int listenPort = 8888;
    private int serverWorkerThreads = 8;
    private int serverSelectorThreads = 3;
    private int serverChannelMaxIdleTimeSeconds = 120;

    private int serverSocketSndBufSize = Integer.parseInt(System.getProperty("com.jdb.remoting.socket.sndbuf.size", "65535"));;
    private int serverSocketRcvBufSize = Integer.parseInt(System.getProperty("com.jdb.remoting.socket.rcvbuf.size", "65535"));;
    private boolean serverPooledByteBufAllocatorEnable = false;
    private int backLogRequest=1024;

    public int getBackLogRequest() {
        return backLogRequest;
    }

    public void setBackLogRequest(int backLogRequest) {
        this.backLogRequest = backLogRequest;
    }

    public int getListenPort() {
        return listenPort;
    }


    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }


    public int getServerWorkerThreads() {
        return serverWorkerThreads;
    }


    public void setServerWorkerThreads(int serverWorkerThreads) {
        this.serverWorkerThreads = serverWorkerThreads;
    }


    public int getServerSelectorThreads() {
        return serverSelectorThreads;
    }


    public void setServerSelectorThreads(int serverSelectorThreads) {
        this.serverSelectorThreads = serverSelectorThreads;
    }


    public int getServerChannelMaxIdleTimeSeconds() {
        return serverChannelMaxIdleTimeSeconds;
    }


    public void setServerChannelMaxIdleTimeSeconds(int serverChannelMaxIdleTimeSeconds) {
        this.serverChannelMaxIdleTimeSeconds = serverChannelMaxIdleTimeSeconds;
    }


    public int getServerSocketSndBufSize() {
        return serverSocketSndBufSize;
    }


    public void setServerSocketSndBufSize(int serverSocketSndBufSize) {
        this.serverSocketSndBufSize = serverSocketSndBufSize;
    }


    public int getServerSocketRcvBufSize() {
        return serverSocketRcvBufSize;
    }


    public void setServerSocketRcvBufSize(int serverSocketRcvBufSize) {
        this.serverSocketRcvBufSize = serverSocketRcvBufSize;
    }


    public boolean isServerPooledByteBufAllocatorEnable() {
        return serverPooledByteBufAllocatorEnable;
    }


    public void setServerPooledByteBufAllocatorEnable(boolean serverPooledByteBufAllocatorEnable) {
        this.serverPooledByteBufAllocatorEnable = serverPooledByteBufAllocatorEnable;
    }
}
