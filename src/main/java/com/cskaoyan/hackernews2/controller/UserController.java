package com.cskaoyan.hackernews2.controller;

import com.cskaoyan.hackernews2.bean.ActiveUser;
import com.cskaoyan.hackernews2.bean.Msg;
import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.service.MsgService;
import com.cskaoyan.hackernews2.service.Uservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/user")
public class UserController {
    @Autowired
    Uservice uservice;
    @Autowired
    MsgService msgService;
    @RequestMapping("/user/{value}")
    public String userinfo(@PathVariable String value, Model model, HttpServletRequest request){

       User user=uservice.findUserById(value);
        model.addAttribute("user",user);

        String   contextPath=request.getContextPath();
        model.addAttribute("contextPath",contextPath);
        return "personal";

    }
    @RequestMapping("/user/tosendmsg")
    public String sendMsg(HttpServletRequest request,Model model){
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath",contextPath);

        return "sendmsg";
    }

    @RequestMapping("/user/msg/addMessage")
    @ResponseBody
    public Map addMessage(HttpSession session,String toName, String content){
        User user = (User) session.getAttribute("user");
        User toname = uservice.findByUserName(toName);
        HashMap<String,Object> map=new HashMap<>();
        Msg msg=new Msg();
        msg.setToid(toname.getId());
        msg.setContent(content);
        msg.setFromid(user.getId());
        msg.setConversationid(toname.getId()+"_"+user.getId());
        try {
            msgService.addMsg(msg);
            map.put("code",0);

        }catch (Exception e){
            map.put("code",1);
        }

    return map;

    }

}
