package lucky.net.rpc.service.provicer.processor;

import org.jupiter.common.util.internal.logging.InternalLogger;
import org.jupiter.common.util.internal.logging.InternalLoggerFactory;
import org.lucky.transport.Status;
import org.lucky.transport.channel.LChannel;
import org.lucky.transport.payload.LRequestBytes;
import org.lucky.transport.processor.ProviderProcessor;

import java.util.concurrent.ThreadPoolExecutor;

import static org.jupiter.common.util.StackTraceUtil.stackTrace;

/**
 * @Author:chaoqiang.zhou
 * @Description:默认的服务端实现操作
 * @Date:Create in 14:12 2018/2/8
 */
public class DefaultProviderProcessor implements ProviderProcessor {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultProviderProcessor.class);

    //服务端的线程可以动态进行配置
    private final ThreadPoolExecutor executor;

    public DefaultProviderProcessor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void handleRequest(LChannel channel, LRequestBytes request) throws Exception {
        if (executor == null) {

        }
    }

    @Override
    public void handleException(LChannel channel, LRequestBytes request, Status status, Throwable cause) {
        logger.error("An exception was caught while processing request: {}, {}.",
                channel.remoteAddress(), stackTrace(cause));


    }

}
