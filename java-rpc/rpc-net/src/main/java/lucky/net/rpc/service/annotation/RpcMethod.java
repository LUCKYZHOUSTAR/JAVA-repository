package lucky.net.rpc.service.annotation;



import lucky.net.rpc.service.FailMode;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface RpcMethod {
    /**
     * 方法名称
     * @return
     */
    String name() default "";

    /**
     * 方法描述
     * @return
     */
    String description() default "";

    /**
     * 失败处理模式
     * @return
     */
    FailMode fail() default FailMode.FailOver;
}
