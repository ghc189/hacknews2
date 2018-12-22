package com.cskaoyan.hackernews2.asyncevent.handler;


import com.cskaoyan.hackernews2.asyncevent.Event;
import com.cskaoyan.hackernews2.asyncevent.EventHandler;
import com.cskaoyan.hackernews2.asyncevent.EventType;
import com.cskaoyan.hackernews2.bean.Msg;
import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.service.MsgService;
import com.cskaoyan.hackernews2.service.NewsService;
import com.cskaoyan.hackernews2.service.Uservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class MessageHandler implements EventHandler {
    @Autowired
    Uservice uservice;
    @Autowired
    NewsService newsService;
    @Autowired
    MsgService msgService;
    //Message处理程序对哪些事件感兴趣
    @Override
    public List<EventType> registerCareEvent() {

        List<EventType> careEventList =new ArrayList<>();
        careEventList.add(EventType.Like);
        careEventList.add(EventType.DISLIKE);
        careEventList.add(EventType.COMMENT);
        return careEventList;
    }

    @Override
    public void handleEvent(Event event) {

        EventType eventType = event.getEventType();

        User FromUser = uservice.findUserById(event.getActorId());
        User toname = uservice.findUserById(event.getOwnerId());
        String content="用户"+FromUser.getUsername()+"赞了你的资讯"+newsService.queryNewById(event.getItemId()).getLink();

        Msg msg=new Msg();
        msg.setToid(toname.getId());
        msg.setContent(content);
        msg.setFromid(FromUser.getId());
        msg.setConversationid(FromUser.getId()+"_"+toname.getId());
        msgService.addMsg(msg);

        System.out.println( "系统发送了一条站内信"+event.getOwnerId()+"到数据库" );


    }
}
