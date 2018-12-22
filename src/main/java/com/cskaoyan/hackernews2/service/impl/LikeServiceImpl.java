package com.cskaoyan.hackernews2.service.impl;

import com.cskaoyan.hackernews2.asyncevent.Event;
import com.cskaoyan.hackernews2.asyncevent.EventProducer;
import com.cskaoyan.hackernews2.asyncevent.EventType;
import com.cskaoyan.hackernews2.dao.NewsMapper;
import com.cskaoyan.hackernews2.dao.Userdao;
import com.cskaoyan.hackernews2.service.LikeService;
import com.cskaoyan.hackernews2.service.NewsService;
import com.cskaoyan.hackernews2.service.Uservice;
import com.cskaoyan.hackernews2.util.JedisUtils;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    EventProducer producer;
    @Autowired
    Userdao userdao;

    @Override
    public void like(int newsId, int uid) {
      /*  Jedis jedis = JedisUtils.getJedisFromPool();
        String s=uid+"_"+newsId+"_like";
        String s2=uid+"_"+newsId+"_dislike";
        jedis.srem(s2,"1");
        jedis.sadd(s,"1");
        int likeCount = getLikeCount(newsId);
        newsMapper.likeCountById(likeCount+1,newsId);*/

        Jedis jedis = JedisUtils.getJedisFromPool();
        String s = newsId + "_like";
        String s2 = newsId + "_dislike";
        jedis.srem(s2, Integer.toString(uid));
        jedis.sadd(s, Integer.toString(uid));


        //触发点赞事件

        Event event = new Event();
        event.setEventType(EventType.Like);  //点赞
        event.setItemType("0");//gei 新闻点赞
        event.setItemId(Integer.toString(newsId));//gei 哪一条新闻

        //是谁触发的？
        event.setActorId(Integer.toString(uid));

        //通过新闻的newsid去查询当前的news是谁的

        int ownerID=newsMapper.selectownerIdByNid(newsId);
        event.setOwnerId(ownerID+"");


        producer.fireEvent(event);





        if(jedis!=null)
            jedis.close();

    }

    @Override
    public void dislike(int newsId, int uid) {
       /* Jedis jedis = JedisUtils.getJedisFromPool();
        String s=uid+"_"+newsId+"_dislike";
        String s2=uid+"_"+newsId+"_like";
        jedis.sadd(s,"1");
        jedis.srem(s2,"1");
        int likeCount = getLikeCount(newsId);
        newsMapper.likeCountById(likeCount-1,newsId);*/


        Jedis jedis = JedisUtils.getJedisFromPool();
        String s = newsId + "_like";
        String s2 = newsId + "_dislike";
        jedis.srem(s, Integer.toString(uid));
        jedis.sadd(s2, Integer.toString(uid));
        if(jedis!=null)
            jedis.close();

    }

    @Override
    public int getLikeCount(int newsId) {
       /* Jedis jedis = JedisUtils.getJedisFromPool();
        String s=newsId+"_like";
        String strid = Integer.toString(newsId);
        int LikeCount=0;
        if (jedis.exists(s)) {
            String s1 = jedis.get(strid);
            LikeCount= Integer.parseInt(s1);
        }*/
        // int LikeCount = newsMapper.selectLikeCountById(newsId);

        Jedis jedis = JedisUtils.getJedisFromPool();
        String s = newsId + "_like";
        Integer LikeCount = Math.toIntExact(jedis.scard(s));
        // Integer LikeCount = Integer.parseInt(String.valueOf());
        if(jedis!=null)
            jedis.close();
        return LikeCount;
    }

    @Override
    public int getdisLikeCount(int newsId) {
        Jedis jedis = JedisUtils.getJedisFromPool();
        String s = newsId + "_dislike";


        Integer disLikeCount = Math.toIntExact(jedis.scard(s));
        // Integer LikeCount = Integer.parseInt(String.valueOf());
        if(jedis!=null)
            jedis.close();

        return disLikeCount;
    }

    @Override
    public int getLike(int newsId, int uid) {
       /* Jedis jedis = JedisUtils.getJedisFromPool();
        String s2=uid+"_"+newsId+"_dislike";
        String s=uid+"_"+newsId+"_like";
       int i=0;
        if (jedis.sismember(s,"1")){
            i=1;
        }else if (jedis.sismember(s2,"1")){
            i=-1;
        }*/
        Jedis jedis = JedisUtils.getJedisFromPool();
        String s2 = newsId + "_dislike";
        String s = +newsId + "_like";
        int i = 0;
        if (jedis.sismember(s, Integer.toString(uid))) {
            i = 1;
        } else if (jedis.sismember(s2, Integer.toString(uid))) {
            i = -1;
        }
        if(jedis!=null)
            jedis.close();
        return i;
    }
}
