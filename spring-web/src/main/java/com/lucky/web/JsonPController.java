package com.lucky.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:http://jinnianshilongnian.iteye.com/blog/1866350
 * @Date:Create in 11:40 2017/12/4
 */
@ControllerAdvice
public class JsonPController {
    @ExceptionHandler(Exception.class)
    public String hello(String name) {
        return "hello world!";
    }
}
