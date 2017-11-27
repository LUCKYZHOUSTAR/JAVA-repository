package cmc.lucky.basic.production.server.acceptor;

import cmc.lucky.basic.production.common.NettyEvent;
import cmc.lucky.basic.production.common.ServiceThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:30 2017/11/27
 */
public abstract class DefaultSrvAcceptor extends NettySrvAcceptor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSrvAcceptor.class);

    protected final NettyEventExecuter nettyEventExecuter = new NettyEventExecuter();
    public void putNettyEvent(final NettyEvent event) {
        this.nettyEventExecuter.putNettyEvent(event);
    }
    public DefaultSrvAcceptor(SocketAddress localAddress) {
        super(localAddress);
    }



    class NettyEventExecuter extends ServiceThread{
        private final LinkedBlockingQueue<NettyEvent> eventQueue = new LinkedBlockingQueue<NettyEvent>();
        private final int MaxSize = 10000;

        public void putNettyEvent(final NettyEvent event) {
            if (this.eventQueue.size() <= MaxSize) {
                this.eventQueue.add(event);
            }
            else {
                logger.warn("event queue size[{}] enough, so drop this event {}", this.eventQueue.size(),
                        event.toString());
            }
        }


        @Override
        public String getServiceName() {
            return NettyEventExecuter.class.getSimpleName();
        }


        @Override
        public void run() {
            logger.info(this.getServiceName() + " service started");
            final ChannelEventListener listener = DefaultSrvAcceptor.this.getChannelEventListener();
            while (!this.isStoped()) {
                try {
                    NettyEvent event = this.eventQueue.poll(3000, TimeUnit.MILLISECONDS);
                    if (event != null && listener != null) {
                        switch (event.getType()) {
                            case IDLE:
                                listener.onChannelIdle(event.getRemoteAddr(), event.getChannel());
                                break;
                            case CLOSE:
                                listener.onChannelClose(event.getRemoteAddr(), event.getChannel());
                                break;
                            case CONNECT:
                                listener.onChannelConnect(event.getRemoteAddr(), event.getChannel());
                                break;
                            case EXCEPTION:
                                listener.onChannelException(event.getRemoteAddr(), event.getChannel());
                                break;
                            default:
                                break;

                        }
                    }
                }catch (Exception e){
                    logger.warn(this.getServiceName() + " service has exception. ", e);

                }

            }
        }
    }

    protected abstract ChannelEventListener getChannelEventListener();

}




