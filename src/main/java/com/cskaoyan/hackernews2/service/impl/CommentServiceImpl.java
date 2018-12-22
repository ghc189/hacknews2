package com.cskaoyan.hackernews2.service.impl;

import com.cskaoyan.hackernews2.bean.Comment;
import com.cskaoyan.hackernews2.dao.CommentMapper;
import com.cskaoyan.hackernews2.service.CommentService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<Comment> findComments() {
        List<Comment> comments=commentMapper.selectComments();
        return comments;
    }

    @Override
    public int addComment(String newsId, String content,int uid) {
        Comment comment=new Comment();
        int i = Integer.parseInt(newsId);
        comment.setNewsid(i);
        comment.setContent(content);
        comment.setUid(uid);
        int insert = commentMapper.insert(comment);
        return insert;
    }

    @Override
    public List<Comment> findCommentsByNid(String value) {
        int NewsId = Integer.parseInt(value);
        List<Comment> commentList= commentMapper.selectByNewsId(NewsId);
        return commentList;
    }
}
