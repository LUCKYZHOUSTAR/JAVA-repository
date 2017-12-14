package com.lucky.task.client;

import com.lucky.task.client.service.ExecutorFactory;
import com.lucky.task.core.config.ServerOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:58 2017/12/14
 */
@Slf4j
public class TaskApplication {
    protected Class<?> bootClass;
    protected SpringApplication app;
    protected ApplicationContext ctx;
    protected JobClientScheduler jobClientScheduler;
    protected ServerOptions options;
    String[] args;


    public TaskApplication(Class<?> bootClass, String[] args, ServerOptions options) {
        this.options = options;
        this.bootClass = bootClass;
        this.args = args;
        this.app = new SpringApplication(bootClass);
        this.jobClientScheduler = new JobClientScheduler(options);
        this.initialize();

    }


    public void registerTask(String name, Executor executor) {
        ExecutorFactory.register(name, executor);
        log.info("register task:{}--{}", name, executor.getClass().getName());

    }

    protected void initialize() {


        Map<String, Object> defaultProps = new HashMap();
        this.initDefaultProperties(defaultProps);
        if (defaultProps.size() > 0) {
            this.app.setDefaultProperties(defaultProps);
        }

    }

    protected void initDefaultProperties(Map<String, Object> props) {
        props.put("spring.main.show-banner", Boolean.valueOf(false));
    }

    private void scanTask() {
        Map<String, Executor> beans = ctx.getBeansOfType(Executor.class);
        log.info("find{} tasks", beans.size());
        beans.forEach((k, executor) -> {
            Class<?> clazz = executor.getClass();
            Task task = clazz.getAnnotation(Task.class);
            String name = (task == null || StringUtils.isEmpty(task.name())) ? clazz.getSimpleName() : task.name();
            this.registerTask(name, executor);
        });
    }

    private void load() {
        try {
            scanTask();
            jobClientScheduler.start();
        } catch (Exception e) {
            jobClientScheduler.destroy();
        }
    }


    public ApplicationContext run() {
        ctx = app.run(this.args);
        this.load();
        log.info("server start:{} success", options.getPort());
        return ctx;
    }

    public <T> T getBean(Class<T> clazz) {
        return getBean(clazz, true);
    }

    public <T> T getBean(Class<T> clazz, boolean autowire) {
        if (ctx == null) {
            throw new RuntimeException("you must wait for application running");
        }

        T bean = ctx.getBean(clazz);
        if (bean == null && !clazz.isInterface()) {
            try {
                bean = clazz.newInstance();
                if (autowire) {
                    ctx.getAutowireCapableBeanFactory().autowireBean(bean);
                }
            } catch (Exception e) {
                String error = String.format("create bean instance of [%s] failed", clazz.getName());
                throw new RuntimeException(error, e);
            }
        }
        return bean;
    }

}
