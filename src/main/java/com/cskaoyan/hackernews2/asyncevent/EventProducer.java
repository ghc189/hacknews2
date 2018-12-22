package com.cskaoyan.hackernews2.asyncevent;


import com.alibaba.fastjson.JSONObject;

import com.cskaoyan.hackernews2.util.JedisUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class EventProducer {



    //本质上是把一个表示点赞事件的事件对象
    //放到一个消息队列
    public void fireEvent(Event event) {

        //把当前的事件放入到事件的队列


        Jedis jedisFromPool = JedisUtils.getJedisFromPool();

        //把当前的Event变成Json的字符串
        String jsonString = JSONObject.toJSONString(event);
        System.out.println("jsonstring="+jsonString);

        //放入到redis的队列
        jedisFromPool.lpush("asyncEventQueue", jsonString);


    }
}
