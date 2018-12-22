package com.cskaoyan.hackernews2.service;

import com.cskaoyan.hackernews2.bean.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findComments();

    int addComment(String newsId, String content,int uid);

    List<Comment> findCommentsByNid(String value);
}
