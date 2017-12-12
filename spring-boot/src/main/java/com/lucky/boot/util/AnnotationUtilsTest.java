package com.lucky.boot.util;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @Author:chaoqiang.zhou
 * @Description:注解工具类信息
 * @Date:Create in 14:21 2017/12/12
 */
public class AnnotationUtilsTest {

    public static void main(String[] args) {

        AnnotationUtils.findAnnotation(AnnotationUtilsTest.class, SpringBootApplication.class);

    }
}
