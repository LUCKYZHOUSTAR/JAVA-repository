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
package com.java.common.feign.converter;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

/**
 * TextplainMessageConverter，集成jackson的序列化
 * 支撑text-plain的范湖模式
 */
@Component("feignTextMessageConverter")
@ConditionalOnClass(FeignClient.class)
@ConditionalOnBean(annotation = EnableFeignClients.class)
public class TextMessageConverter extends MappingJackson2HttpMessageConverter {
    public TextMessageConverter() {
        /*text/plain  和 text/html的 content-type的支持*/
        setSupportedMediaTypes(Lists.newArrayList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.valueOf("text/json")));
    }
}
