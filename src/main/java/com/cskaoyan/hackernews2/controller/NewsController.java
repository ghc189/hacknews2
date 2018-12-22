package com.cskaoyan.hackernews2.controller;

import com.cskaoyan.hackernews2.bean.*;
import com.cskaoyan.hackernews2.service.CommentService;
import com.cskaoyan.hackernews2.service.NewsService;
import com.cskaoyan.hackernews2.service.Uservice;
import com.cskaoyan.hackernews2.util.AliFileUpload;
import com.cskaoyan.hackernews2.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class NewsController {
    @Autowired
    NewsService newsService;
    @Autowired
    AliFileUpload aliFileUpload;
    @Autowired
    Uservice uservice;
    @Autowired
    CommentService commentService;
    @RequestMapping("/user/addNews")
    public String addNews(@Valid News news, HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        news.setUid(id);
        newsService.addnews(news);
    return "redirect:/";
    }
    @RequestMapping("/uploadImage")
    @ResponseBody
    public Map uploadImage(MultipartFile file){
        HashMap<String,Object> map=new HashMap<>();
        if (file==null){
            map.put("error","Not Found");
            map.put("message","No message available");
            map.put("path","/uploadImage/");
            map.put("status",404);

        }


        try {
            String imgUrl = aliFileUpload.upload(file);
            map.put("code",0);
            map.put("msg",imgUrl);
        } catch (IOException e) {
            map.put("code",1);
            e.printStackTrace();
        }

        return map;

    }

@RequestMapping("/news/{value}")
    public String commentNews(@PathVariable String value, Model model, HttpServletRequest request){
        News news=newsService.queryNewById(value);
        Integer uid = news.getUid();
    User owner = uservice.findUserById(uid.toString());
    model.addAttribute("news",news);
    model.addAttribute("owner",owner);
    String   contextPath=request.getContextPath();
    model.addAttribute("contextPath",contextPath);
    Integer likeCount = news.getLikeCount();
    model.addAttribute("like",likeCount);
    List<Comment> comments=commentService.findCommentsByNid(value);
    List<CommentVo> commentVos=new LinkedList<>();
    for (Comment c:comments
         ) {
        CommentVo commentVo=new CommentVo();
        Integer uid1 = c.getUid();
        User userById = uservice.findUserById(uid1.toString());
       commentVo.setComment(c);
       commentVo.setUser(userById);
       commentVos.add(commentVo);
    }
    model.addAttribute("comments",commentVos);
    return "detail";

}

}
