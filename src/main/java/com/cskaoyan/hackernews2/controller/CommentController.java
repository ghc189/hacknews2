package com.cskaoyan.hackernews2.controller;

import com.cskaoyan.hackernews2.bean.User;
import com.cskaoyan.hackernews2.service.CommentService;
import com.cskaoyan.hackernews2.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
@Autowired
    CommentService commentService;
@Autowired
    NewsService newsService;
    @RequestMapping("/addComment")
    public String addComment(String newsId, String content, HttpSession session){

        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        int i=commentService.addComment(newsId,content,uid);
        newsService.updateCommentCount(newsId);
        return "redirect:/news/"+newsId;
    }
}
