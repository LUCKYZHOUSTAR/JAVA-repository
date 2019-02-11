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

import com.google.common.collect.Maps;
import com.java.common.jersey.EnableJersey;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * DefaultUniteHeaderFeignEncoder
 * 默认的header的统一处理器

 */
@Configuration
@ConditionalOnBean(annotation = EnableJersey.class, value = SelectFeignEncoder.class)
public class UniteHeaderFeignEncoderConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public AbstractUniteHeaderFeignEncoder abstractUniteHeaderFeignEncoder() {
        return new AbstractUniteHeaderFeignEncoder() {
            /**
             * 空的map
             */
            private Map<String, String> map = Maps.newHashMap();

            @Override
            protected Map<String, String> getUniteHeaders() {
                return map;
            }
        };
    }
}
