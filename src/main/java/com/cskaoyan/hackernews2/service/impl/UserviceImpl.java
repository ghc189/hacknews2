package com.cskaoyan.hackernews2.service.impl;

import com.cskaoyan.hackernews2.bean.ActiveUser;
import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.dao.Userdao;
import com.cskaoyan.hackernews2.service.Uservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserviceImpl implements Uservice {
    @Autowired
    Userdao userdao;
    @Override
    public String Userlogin(User user) {
       User user1=null;
        user1=userdao.queryUsername(user.getUsername());
        if (user1==null)
            return "invalidName";
      user1=userdao.findUsernameAndPasswd(user);
       if (user1==null)
           return "invalidPasswd";

        return "valid";
    }

    @Override
    public int Reglister(User user) {
        return userdao.insertUser(user);
    }

    @Override
    public User findByUserName(String username) {
        User user = userdao.queryUsername(username);

        return user;
    }

    @Override
    public User findUserById(String value) {
        int i = Integer.parseInt(value);
        User user=userdao.findUserById(i);
        return user;
    }
}
