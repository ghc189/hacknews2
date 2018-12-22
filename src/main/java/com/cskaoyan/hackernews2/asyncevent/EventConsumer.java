package com.cskaoyan.hackernews2.asyncevent;


//组件先要注册自己对那些事件感兴趣
//提供一个对应事件的回调函数，如果对应的事件发生之后，可以让EventConsumer去回调这个处理函数



//从redis的队列里取出事件的json字符串，然后转成对应Event对象
//通知对这件事情感兴趣的组件


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.cskaoyan.hackernews2.util.JedisUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;


@Component
public class EventConsumer implements ApplicationContextAware, InitializingBean {



    private  ApplicationContext applicationContext;

    public void dispatchEvent(){

        Jedis jedisFromPool = JedisUtils.getJedisFromPool();

        //超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。
        //
        List<String> asyncEventQueue = jedisFromPool.brpop(0, "asyncEventQueue");

        String jsonEvent = asyncEventQueue.get(1);

        //JSONObject.toJavaObject(JSON)

        Event event = JSON.parseObject(jsonEvent, Event.class);

        System.out.println(event);

        List<EventHandler> eventHandlers = eventRegisterTable.get(event.getEventType());


        //这些处理函数在子线程里做 ： 异步
        for (EventHandler handler: eventHandlers) {

            handler.handleEvent(event);
        }

        jedisFromPool.close();


    }



    //保存事件和对应事件的处理器集合
    private HashMap<EventType,List<EventHandler>>  eventRegisterTable=new HashMap<>();



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    //    //等待spring的bean container初始化完毕 再去容器去里搜索EventHandler的实现类
    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, EventHandler> beansOfEventHandler = applicationContext.getBeansOfType(EventHandler.class);

        //先去拿到所有的handler

        Collection<EventHandler> values = beansOfEventHandler.values();

        for (EventHandler handler :values) {

            List<EventType> eventTypes = handler.registerCareEvent();

            for   (EventType e :eventTypes) {
                if (eventRegisterTable.containsKey(e) ){
                    List<EventHandler> eventHandlers = eventRegisterTable.get(e);
                    eventHandlers.add(handler);
                }else{

                    List<EventHandler> eventHandlers =new ArrayList<>();
                    eventHandlers.add(handler);
                    eventRegisterTable.put(e,eventHandlers);
                }
            }

        }


        //当上门的代码执行完毕之后，已经获取了事件 和对事件感兴趣的handler列表 构成的一个表结构 eventRegisterTable

        //去事件队列里取事件，并分发出去
        new Thread( new Runnable(){

            @Override
            public void run() {

                while(true){
                    dispatchEvent();
                }
            }
        }).start();




    }
}
