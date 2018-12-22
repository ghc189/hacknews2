package com.cskaoyan.hackernews2.controller;

import com.cskaoyan.hackernews2.bean.ActiveUser;
import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.service.Uservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
//@RequestMapping("/user")
public class LoginController {
    @Autowired
    Uservice uservice;
    @RequestMapping("/login")
    @ResponseBody
    public Map login(@Valid User user, HttpSession session){
        HashMap<String,Object> map=new HashMap<>();

        String userlogin = uservice.Userlogin(user);

        if ("invalidName".equals(userlogin)){
            map.put("msgname","用户名不存在");
            map.put("code",1);
        }if ("invalidPasswd".equals(userlogin)){
            map.put("msgpwd","密码不正确");
            map.put("code",1);
        }else if ("valid".equals(userlogin)){
            User byUserName = uservice.findByUserName(user.getUsername());
            session.setAttribute("user",byUserName);
                map.put("code",0);
        }
        return map;

    }

    @RequestMapping("/reg")
    @ResponseBody
    public Map reg(@Valid User user,HttpSession session){
        HashMap<String,Object> map=new HashMap<>();

        User byUserName = uservice.findByUserName(user.getUsername());
        if (byUserName!=null){
            map.put("msgname","用户名已存在");
            return map;
        }else {
            Random random = new Random();
            int i1 = random.nextInt(900) + 100;
            String headUrl = "http://images.nowcoder.com/head/" + i1 + "t.png";
            user.setHeadUrl(headUrl);
            int i = uservice.Reglister(user);
            session.setAttribute("user", user);
            map.put("code", 0);
        }
        return map;
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session!=null)
            session.invalidate();
        return "redirect:/";
    }

}
