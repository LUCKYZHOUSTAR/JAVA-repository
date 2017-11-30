package com.lucky.ioc;

import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:10 2017/11/30
 */
@Service
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id; //用户编号
    private String name; //用户名
    private int age; //用户年龄
    private String gender; //用户性别

    public User() {
    }

    public User(int id, String name, int age, String gender) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age
                + ", gender=" + gender + "]";
    }
}
