package lucky.net.rpc.service.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Inherited
@Documented
public @interface RpcParameter {
    /**
     * 方法参数或返回值名称
     * @return
     */
    String name() default "";

    /**
     * 方法参数或返回值描述
     * @return
     */
    String description() default "";
}
