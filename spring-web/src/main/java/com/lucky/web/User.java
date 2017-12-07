package com.lucky.web;

import java.util.Date;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:22 2017/12/7
 */
public class User {
    private int id;

    private String name;

    private int age;

    private Date birDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirDate() {
        return birDate;
    }

    public void setBirDate(Date birDate) {
        this.birDate = birDate;
    }
}
