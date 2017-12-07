package com.lucky.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:22 2017/12/7
 */
@RestController
@RequestMapping(value = "/index")
public class IndexController {
    @Value(value = "${study.secret}")
    private String secret;

    @Value(value = "${study.number}")
    private int id;

    @Value(value = "${study.number}")
    private int age;

    @Value(value = "${study.desc}")
    private String desc;

    @RequestMapping
    public String index() {
        return "hello springboot!";
    }

    // @RequestParam 简单类型的绑定，可以出来get和post
    @RequestMapping(value = "/get")
    public HashMap<String, Object> get(@RequestParam String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello zhenqi");
        map.put("name", name);
        map.put("age",age);
        map.put("secret", secret);
        map.put("id", id);
        map.put("desc", desc);
        return map;
    }

    // @PathVariable 获得请求url中的动态参数
    @RequestMapping(value = "/get/{id}/{name}/{age}")
    public User getUser(@PathVariable int id, @PathVariable String name, @PathVariable int age) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        user.setBirDate(new Date());
        return user;
    }
}
