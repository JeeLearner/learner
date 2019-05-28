package com.jee.demo.reflect.base;

import com.jee.demo.api.User;

import java.util.List;

public interface IBusiness {
    public int getPrice(String good);

    public List<User> list(User user);
}