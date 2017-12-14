package com.lucky.task.client;

import java.lang.annotation.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:具体的task执行器注解信息
 * @Date:Create in 10:57 2017/12/14
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Task {
    String name() default "";

}
