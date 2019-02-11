/**
 * 佛祖镇楼                  BUG辟易
 * 佛曰:
 * 写字楼里写字间，写字间里程序员；
 * 程序人员写程序，又拿程序换酒钱。
 * 酒醒只在网上坐，酒醉还来网下眠；
 * 酒醉酒醒日复日，网上网下年复年。
 * 但愿老死电脑间，不愿鞠躬老板前；
 * 奔驰宝马贵者趣，公交自行程序员。
 * 别人笑我忒疯癫，我笑自己命太贱；
 * 不见满街漂亮妹，哪个归得程序员？
 */
package com.java.common.feign.encode;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * AesRequestInterceptor
 * <p>

 */
@Component
@ConditionalOnBean(annotation = EnableFeignClients.class, value = FeignEncoder.class)
public class RequestEncoder implements RequestInterceptor, InitializingBean {

    /**
     * slf4j日志
     */
    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 编码器的列表
     */
    @Autowired
    private List<SelectFeignEncoder> feignEncoders;
    /**
     * header头的统一处理
     */
    @Autowired
    private AbstractUniteHeaderFeignEncoder abstractUniteHeaderFeignEncoder;
    /**
     * 默认的解码器
     */
    private SelectFeignEncoder defaultFeignEncoder = t -> {
    };

    @Override
    public void apply(RequestTemplate template) {
        abstractUniteHeaderFeignEncoder.encode(template);
        if (!CollectionUtils.isEmpty(feignEncoders)) {
            SelectFeignEncoder current = feignEncoders.stream().filter(e -> e.isEncode(template)).findFirst().orElse(defaultFeignEncoder);
            log.info("current is {}", current.getClass());
            try {
                current.encode(template);
            } catch (Exception e) {
                log.error("encode template error", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (!CollectionUtils.isEmpty(feignEncoders)) {
            log.info("feign encode size is {}", feignEncoders.size());
            feignEncoders.forEach(feignEncoder -> log.info(feignEncoder.getClass().getName()));
        }
    }
}
