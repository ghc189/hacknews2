package com.cskaoyan.hackernews2.service;

import com.cskaoyan.hackernews2.bean.ActiveUser;
import com.cskaoyan.hackernews2.bean.User;

public interface Uservice {
    public String Userlogin(User user);

    int Reglister(User user);

    User findByUserName(String username);

    User findUserById(String value);
}
