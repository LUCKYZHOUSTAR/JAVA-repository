package basic.netty.server;

import basic.netty.data.Request;

import java.util.List;
import java.util.function.Function;

/**
 * @Author:chaoqiang.zhou
 * @Description:核心server端负责处理任务的请求
 * @Date:Create in 13:27 2017/11/28
 */
public interface Server<Job extends Request> {

    /**
     * 服务端开始
     */
    void start();

    //服务端结束
    void shutDown();



    void execute(Job job);

    void execute(List job);

}
