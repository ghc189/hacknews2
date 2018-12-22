package com.cskaoyan.hackernews2.controller;

import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.service.LikeService;
import com.cskaoyan.hackernews2.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {
    @Autowired
    NewsService newsService;
    @Autowired
    LikeService likeService;
    @RequestMapping("/like")
    @ResponseBody
    public Map like(String newsId, HttpSession session){
        HashMap<String,Object> map=new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user==null){
            map.put("pop",0);
            return map;
        }

        try {
            likeService.like(Integer.parseInt(newsId),user.getId());
            int likeCount = likeService.getLikeCount(Integer.parseInt(newsId));
            map.put("code",0);
            map.put("msg",likeCount);
        }catch (Exception e){
        map.put("code",1);
        }
    return map;

    }



    @RequestMapping("/dislike")
    @ResponseBody
    public Map dislike(String newsId,HttpSession session){
        HashMap<String,Object> map=new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user==null){
            map.put("pop",0);
            return map;
        }
        try{
            likeService.dislike(Integer.parseInt(newsId),user.getId());
            int likeCount = likeService.getLikeCount(Integer.parseInt(newsId));
            map.put("code",0);
            map.put("msg",likeCount);
        }catch (Exception e){
            map.put("code",1);
        }
        return map;

    }
}
