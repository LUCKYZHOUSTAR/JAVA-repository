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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * FormHttpMessageConverter
 * 支撑formParam的调用
 */
@Component
@SuppressWarnings("all")
@ConditionalOnClass(FeignClient.class)
@ConditionalOnBean(annotation = EnableFeignClients.class)
public class FormHttpMessageConverter implements
        HttpMessageConverter<Map<String, ?>>, CommandLineRunner {

    private org.springframework.http.converter.FormHttpMessageConverter formHttpMessageConverter;
    @Autowired
    private ApplicationContext applicationContext;

    public FormHttpMessageConverter() {
        /*初始化一个默认的，getSupportedMediaTypes调用的时候不抛出NullPointerException*/
        formHttpMessageConverter = new org.springframework.http.converter.FormHttpMessageConverter();
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        if (!MultiValueMap.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (mediaType == null) {
            return true;
        }

        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            // We can't read multipart....
            if (!supportedMediaType.equals(MediaType.MULTIPART_FORM_DATA) && supportedMediaType.includes(mediaType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (!MultiValueMap.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (mediaType == null || MediaType.ALL.equals(mediaType)) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.isCompatibleWith(mediaType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return formHttpMessageConverter.getSupportedMediaTypes();
    }

    @Override
    public Map<String, ?> read(Class<? extends Map<String, ?>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return formHttpMessageConverter.read(null, inputMessage);
    }

    @Override

    public void write(Map<String, ?> stringMap, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (!isMultipart(stringMap, contentType)) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            if (stringMap instanceof MultiValueMap) {
                map = (MultiValueMap) stringMap;
            } else {
                for (Map.Entry<String, ?> entry : stringMap.entrySet()) {
                    map.add(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : "");
                }
            }
            formHttpMessageConverter.write(map, contentType, outputMessage);
        } else {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            if (stringMap instanceof MultiValueMap) {
                map = (MultiValueMap) stringMap;
            } else {
                for (Map.Entry<String, ?> entry : stringMap.entrySet()) {
                    map.add(entry.getKey(), entry.getValue() != null ? entry.getValue() : null);
                }
            }
            formHttpMessageConverter.write(map, contentType, outputMessage);
        }
    }

    @SuppressWarnings("all")
    private boolean isMultipart(Map<String, ?> map, @Nullable MediaType contentType) {
        if (contentType != null) {
            return MediaType.MULTIPART_FORM_DATA.includes(contentType);
        }
        for (String name : map.keySet()) {
            if (map instanceof MultiValueMap) {
                MultiValueMap<String, ?> multiValueMap = (MultiValueMap) map;
                for (Object value : multiValueMap.get(name)) {
                    if (value != null && !(value instanceof String)) {
                        return true;
                    }
                }
            } else {
                Object value = map.get(name);
                if (value != null && !(value instanceof String)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void run(String... args) {
        HttpMessageConverters converters = applicationContext.getBean(HttpMessageConverters.class);
        for (HttpMessageConverter converter : converters.getConverters()) {
            if (converter instanceof org.springframework.http.converter.FormHttpMessageConverter) {
                this.formHttpMessageConverter = (org.springframework.http.converter.FormHttpMessageConverter) converter;
                break;
            }
        }
    }
}
