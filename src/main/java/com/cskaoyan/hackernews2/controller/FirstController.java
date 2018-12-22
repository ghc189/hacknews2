package com.cskaoyan.hackernews2.controller;

import com.cskaoyan.hackernews2.bean.ActiveUser;
import com.cskaoyan.hackernews2.bean.News;
import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.bean.Vo;
import com.cskaoyan.hackernews2.service.LikeService;
import com.cskaoyan.hackernews2.service.NewsService;
import com.cskaoyan.hackernews2.service.Uservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FirstController {
@Autowired
    NewsService newsService;
@Autowired
    Uservice uservice;
@Autowired
    LikeService likeService;
@RequestMapping("/")
    public String home(HttpServletRequest request, Model model){


    String   contextPath=request.getContextPath();
    model.addAttribute("contextPath",contextPath);

    User userl = (User) request.getSession().getAttribute("user");
    List<Vo> vos = getNews(userl);
    model.addAttribute("vos",vos);
    if (userl==null)
        model.addAttribute("pop",0);


    return "home";
    }

    private List<Vo> getNews(User userl) {
        List<News> newsList = newsService.selectNews();
        List<User> users=new LinkedList<>();
        List<Vo> vos=new LinkedList<>();
        for (News n:newsList
             ) {
            n.setScore(scoreNews(n));
        }
        Collections.sort(newsList);

        for (News n:newsList
        ) {
            Vo vo=new Vo();
            vo.setNews(n);
            Integer uid = n.getUid();
            User user = uservice.findUserById(uid.toString());
            //   System.out.println(n.getCreateDate());

            vo.setUser(user);
            users.add(user);
            Integer nid = n.getId();
            int likeCount = likeService.getLikeCount(nid);
            int dislikeCount=likeService.getdisLikeCount(nid);
            n.setLikeCount(likeCount);

            if (userl!=null) {
                int likecount = likeService.getLike(n.getId(), userl.getId());
                vo.setLike(likecount);
            }else {
                vo.setLike(0);
            }
            vos.add(vo);
            if (vos.size()>10)
                break;
        }


        return vos;
    }
     //	Reddit网站算法，不适用于新闻类
    private int  scoreNews(News news){
        Integer nid = news.getId();
        int likeCount = likeService.getLikeCount(nid);
        int dislikeCount=likeService.getdisLikeCount(nid);
        Date createDate = news.getCreateDate();
        String str="20051108074643";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        long ts=0;
        try {
            long millionSeconds = df.parse(str).getTime();
            long time = createDate.getTime();
             ts=(time-millionSeconds)/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int x=likeCount-dislikeCount;
        int y=0;
        if (x>=0)
            y=1;
       /* else if (x==0)
            y=0;*/
        else
            y=-1;
        int z=0;
        if (Math.abs(x)>=1)
            z=Math.abs(x);
        else
            z=1;
        Double score=Math.log(z)+(y*ts)/45000;

        int i = Integer.parseInt(new DecimalFormat("0").format(score));
        return i;


    }



}
