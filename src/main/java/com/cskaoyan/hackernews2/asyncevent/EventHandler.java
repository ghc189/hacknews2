package com.cskaoyan.hackernews2.asyncevent;

import java.util.List;

public interface EventHandler {


     //注册自己 感兴趣的事件
     List<EventType> registerCareEvent();

    //回调函数 ，处理对应的事件
     void    handleEvent(Event event);


}
