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
package com.java.common.feign.decode;

import com.java.common.util.ReflectUtils;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * UusResponseEntityDecoder
 */
@Component
@ConditionalOnBean(annotation = EnableFeignClients.class)
public class ResponseDecoder implements Decoder, InitializingBean {
    /**
     * slf4j日志
     */
    private Logger log = LoggerFactory.getLogger(getClass());
    /*解码器*/
    private Decoder decoder;
    /*Response.ByteArrayBody的构造方法*/
    private Constructor<?> constructor;
    /*Response的body字段*/
    private Field field;
    @Autowired
    private List<SelectFeignDecoder> feignDecoders;

    private SelectFeignDecoder defaulFeignDecoder = (b) -> b;

    @SuppressWarnings("all")
    public ResponseDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        /*初始化解码器*/
        this.decoder = new SpringDecoder(messageConverters);
        try {
            /*通过反射获取字段和构造函数*/
            field = Response.class.getDeclaredField("body");
            field.setAccessible(true);
            for (Class claz : Response.class.getDeclaredClasses()) {
                if (claz.getSimpleName().equals("ByteArrayBody")) {
                    constructor = claz.getDeclaredConstructor(byte[].class);
                    constructor.setAccessible(true);
                    break;
                }
            }
        } catch (Exception e) {
            throw new Error("get the class error");
        }
    }


    @Override
    public Object decode(final Response response, Type type) throws IOException,
            FeignException {
        if (isParameterizeHttpEntity(type)) {
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            Object decodedObject = decoder.decode(response, type);
            return createResponse(decodedObject, response);
        } else if (isHttpEntity(type)) {
            return createResponse(null, response);
        } else {
            decode(response);
            return decoder.decode(response, type);
        }
    }

    @SuppressWarnings("all")
    private void decode(Response response) throws IOException {
        InputStream in = response.body().asInputStream();
        try {
            if (in.available() > 0) {
                String url = response.request().url();
                SelectFeignDecoder current = feignDecoders.stream().filter(e -> e.isDecode(response)).findFirst().orElse(defaulFeignDecoder);
                byte[] bytes = StreamUtils.copyToByteArray(in);
                /*执行数据解密*/
                if (bytes != null && bytes.length > 0) {
                    if (current != defaulFeignDecoder) {
                        log.info("decript url is {}", response.request().url());
                    }
                    /*设置解密后的body属性*/
                    field.set(response, constructor.newInstance(current.decodeBytes(bytes)));
                } else {
                    log.error("body bytes is null or length is zero");
                }
            }
        } catch (Exception e) {
            log.error("decode error", e);
        } finally {
            ReflectUtils.safeInvokeNoArgMethod(in, InputStream.class, "close");
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.info("feign decode size is {}", feignDecoders.size());
        feignDecoders.forEach(feignDecoder -> log.info(feignDecoder.getClass().getName()));
    }

    private boolean isParameterizeHttpEntity(Type type) {
        if (type instanceof ParameterizedType) {
            return isHttpEntity(((ParameterizedType) type).getRawType());
        }
        return false;
    }

    private boolean isHttpEntity(Type type) {
        if (type instanceof Class) {
            return HttpEntity.class.isAssignableFrom((Class) type);
        }
        return false;
    }

    @SuppressWarnings("all")
    private <T> ResponseEntity<T> createResponse(Object instance, Response response) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        for (String key : response.headers().keySet()) {
            headers.put(key, new LinkedList<>(response.headers().get(key)));
        }
        return new ResponseEntity<>((T) instance, headers, HttpStatus.valueOf(response.status()));
    }
}
