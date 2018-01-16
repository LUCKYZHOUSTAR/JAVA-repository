package com.lucky.spring.flag;

import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author:chaoqiang.zhou
 * @Description:指定个性话的标签，根据标签的值，来加载不通的bean的操作
 * @Date:Create in 15:33 2017/12/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ContentImportSelector.class)
public @interface EnableContentService {
    String policy() default "simple";

    int order() default Ordered.LOWEST_PRECEDENCE;
}


