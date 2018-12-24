package com.lucky.design.patterns.document.abstrac;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/24 15:02
 * @Description:
 */
public interface Document {

    Void put(String key, Object value);

    Object get(String key);

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
