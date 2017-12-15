package com.lucky.task.client.service;

import com.alibaba.fastjson.JSON;
import com.lucky.task.client.Executor;
import com.lucky.task.client.TaskContext;
import com.lucky.task.core.config.ExecuteParam;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.util.Profiler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:chaoqiang.zhou
 * @Description:执行器的具体实现
 * @Date:Create in 14:58 2017/12/13
 */
@Slf4j
public class TaskServiceImpl implements TaskService {

    private Map<String, Executor> executors;
    private ExecutorService pool = Executors.newCachedThreadPool();
    //手动执行缓存map信息
    private ConcurrentMap<String, String> runningTasks = new ConcurrentHashMap<>();


    public TaskServiceImpl(Map<String, Executor> executors) {
        this.executors = executors;
    }


    @Override
    public Response execute(ExecuteParam param) {
        log.info("接收到任务{}", JSON.toJSONString(param));
        //过滤手动执行job，多次在任务执行期间执行
        if (param.getType() == ExecuteParam.ExecuteType.AUTO && runningTasks.containsKey(param.getName())) {
            String error = String.format("任务 %s 正在执行, 跳过此次调度(如果多次发生类型情况, 请检查调度时间是否合理)", param.getName());
            log.warn(error);
            //上报结果信息
            return new Response(true, error);
        }

        String name = param.getName();
        Executor executor = executors.get(name);
        if (executor == null) {
            log.error("任务信息找寻不到，{}", name);
            return new Response(false, "找不到任务信息");
        }
        try {

            if (param.getType() == ExecuteParam.ExecuteType.AUTO) {
                runningTasks.putIfAbsent(param.getName(), param.getName());
            }

            pool.execute(() -> this.execute(executor, param));
        } catch (Exception e) {
            log.error("提交任务线程池失败", e);
            return new Response(false, "提交任务线程池失败" + e.getMessage());
        }
        return new Response(true, null);
    }


    private void execute(Executor executor, ExecuteParam param) {
        Profiler.begin();
        try {
            log.info("开始执行任务{}，执行方式{}", param.getName(), param.getType());
            TaskContext taskContext = new TaskContext(param);
            executor.execute(taskContext);
            //上报结果信息
            log.error("任务执行成功，耗时：{}，", Profiler.end());
        } catch (Exception e) {
            String error = e.getMessage();
            if (StringUtils.isEmpty(error)) {
                error = e.toString();
            }
            //TODO:后续上报结果通知
            log.error("任务执行失败，耗时：{}，错误信息{}", Profiler.end(), error);
        } finally {
            if (param.getType() == ExecuteParam.ExecuteType.AUTO) {
                runningTasks.remove(param.getName());
            }
        }
    }
}
