package com.cskaoyan.hackernews2.controller;


import com.cskaoyan.hackernews2.bean.Conversation;
import com.cskaoyan.hackernews2.bean.MessageVO;
import com.cskaoyan.hackernews2.bean.Msg;
import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.service.MsgService;
import com.cskaoyan.hackernews2.service.Uservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    MsgService msgService;
    @Autowired
    Uservice uservice;
    @RequestMapping("/list")
    public String List(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        Integer uid = user.getId();

        List<Msg> msgList=msgService.selectMsgByUid(uid);
        List<Conversation> conversations=new LinkedList<>();
        Set<User> userSet=new HashSet<>();
        for (Msg m:msgList
             ) {
            Integer fromid = m.getFromid();
            User fromuser = uservice.findUserById(Integer.toString(fromid));
            userSet.add(fromuser);
           /* Conversation conversation=new Conversation();
            conversation.setUser(fromuser);
            conversation.setConversation(m);
            conversations.add(conversation);
            conversation.setUnread(msgList.size());*/
        }
        for (User u:userSet
             ) {

          List<Msg> msgs= msgService.findbyconversationId(u.getId()+"_"+user.getId());
            if (msgs.size()>0) {
                Conversation conversation=new Conversation();
                conversation.setUser(u);
                conversation.setConversation(msgs);
                conversation.setUnread(msgs.size());
                conversation.setConversationid(u.getId() + "_" + user.getId());
                conversation.setCreateddate(msgs.get(0).getCreateddate());

                conversation.setContent(msgs.get(0).getContent() + "...");
                conversations.add(conversation);
            }


        }
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath",contextPath);
        model.addAttribute("conversations",conversations);

     return "letter";
    }

    @RequestMapping("/detail")
    public String MsgDetail( HttpServletRequest request, Model model){
        String conversationId=request.getParameter("conversationId");
       List<Msg> msgList= msgService.findbyconversationId(conversationId);
       List<MessageVO> messages=new LinkedList<>();
        for (Msg m:msgList
        ) {
            Integer fromid = m.getFromid();
            User fromuser = uservice.findUserById(Integer.toString(fromid));
            MessageVO mesg=new MessageVO();
            mesg.setHeadUrl(fromuser.getHeadUrl());
            mesg.setUserId(fromuser.getId());
            mesg.setMessage(m);
            messages.add(mesg);
        }
        String contextPath = request.getContextPath();
        model.addAttribute("contextPath",contextPath);
        model.addAttribute("messages",messages);
        return "letterDetail";

    }
}
