package com.lucky.util;

import org.springframework.util.ClassUtils;


public class MvcUtils {

    private static final String REST_CONTROLLER_CLASS = "org.springframework.web.bind.annotation.RestController";
    private static final boolean REST_CONTROLLER_PRESENT = ClassUtils.isPresent(
            REST_CONTROLLER_CLASS, MvcUtils.class.getClassLoader());

    public static boolean isSpringMvcPresent() {
        return REST_CONTROLLER_PRESENT;
    }

}
