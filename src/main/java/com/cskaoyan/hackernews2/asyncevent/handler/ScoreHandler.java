package com.cskaoyan.hackernews2.asyncevent.handler;


import com.cskaoyan.hackernews2.asyncevent.Event;
import com.cskaoyan.hackernews2.asyncevent.EventHandler;
import com.cskaoyan.hackernews2.asyncevent.EventType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ScoreHandler implements EventHandler {

    //Message处理程序对哪些事件感兴趣
    @Override
    public List<EventType> registerCareEvent() {

        List<EventType> careEventList =new ArrayList<>();
        careEventList.add(EventType.Like);
        careEventList.add(EventType.DISLIKE);
        return careEventList;
    }

    @Override
    public void handleEvent(Event event) {

        EventType eventType = event.getEventType();

        if (eventType.equals(EventType.Like)){




          /*  System.out.println("给"+event.getActorId()+"积分加1");
            System.out.println("给"+event.getOwnerId()+"积分加10");
*/
        }else{

            System.out.println("给"+event.getActorId()+"积分加1");
            System.out.println("给"+event.getOwnerId()+"积分减10");
        }



    }

}
