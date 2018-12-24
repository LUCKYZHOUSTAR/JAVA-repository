package com.lucky.design.patterns.document.abstrac.domain;

import com.lucky.design.patterns.document.abstrac.Document;

import java.util.Optional;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/24 15:50
 * @Description:
 */
public interface HasType  extends Document {

    String PROPERTY = "type";

    default Optional<String> getType(){
        return Optional.ofNullable((String) get(PROPERTY));
    }
}
