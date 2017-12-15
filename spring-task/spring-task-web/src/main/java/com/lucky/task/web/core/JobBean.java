package com.lucky.task.web.core;

import com.alibaba.fastjson.JSON;
import com.lucky.task.core.config.ExecuteParam;
import com.lucky.task.core.config.ServerOptions;
import com.lucky.task.core.enums.ProcessType;
import com.lucky.task.core.net.client.JettyClient;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.net.codec.RpcRequest;
import com.lucky.task.web.schedule.JobInfoFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDate;

/**
 * @Author:chaoqiang.zhou 参考：http://blog.csdn.net/haitaofeiyang/article/details/50737644
 * @Description:并行的job，不是串行的
 * @Date:Create in 16:58 2017/12/14
 */
@Slf4j
public class JobBean extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        TriggerKey triggerKey = context.getTrigger().getKey();
        log.info("task{}，执行器{}开始调度{}", triggerKey.getName(), triggerKey.getGroup(), LocalDate.now());
        //后续实现各种路由的算法
        JobInfoFactory.jobs.get(triggerKey.getGroup()).forEach(data -> {
            trigger(triggerKey.getGroup(), triggerKey.getName(), data);
            return;
        });

    }


    private void trigger(String group, String name, ServerOptions options) {
        String url = "http://" + options.getIp() + ":" + options.getPort() + "/";
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setName(name);
        RpcRequest rpcRequest = new RpcRequest(ProcessType.request.value(), executeParam);
        Response response = JettyClient.send(rpcRequest, url);
        log.info("调度通知下发，名称{}，执行器{}，结果{}", name, group, JSON.toJSONString(response));
    }
}
