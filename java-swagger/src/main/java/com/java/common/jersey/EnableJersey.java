package com.java.common.jersey;

import java.lang.annotation.*;

/**
 * jersey的自动加载，不需要配置
 * 加在启动类上
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableJersey {
    /**
     * swagger的title
     *
     * @return
     */
    String title() default "";

    /**
     * swagger上跳转URL的描述
     *
     * @return
     */
    String author() default "";

    /**
     * 扫描的包
     *
     * @return
     */
    String[] packages() default "com.ehomepay";
}
