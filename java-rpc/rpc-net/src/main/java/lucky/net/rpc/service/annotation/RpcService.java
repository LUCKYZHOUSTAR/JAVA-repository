package lucky.net.rpc.service.annotation;


import lucky.net.rpc.NamingConvention;
import lucky.net.rpc.service.FailMode;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface RpcService {
    /**
     * 服务名称
     * @return
     */
    String name() default "";

    /**
     * 服务描述
     * @return
     */
    String description() default "";

    /**
     * 服务方法命名约定
     * @return
     */
    NamingConvention convention() default NamingConvention.PASCAL;

    /**
     * 失败处理模式
     * @return
     */
    FailMode fail() default FailMode.FailOver;
}
