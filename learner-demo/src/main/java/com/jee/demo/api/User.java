package com.jee.demo.api;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/21
 * @Version:v1.0
 */
public class User implements Serializable {

    private int id;
    private String name;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

