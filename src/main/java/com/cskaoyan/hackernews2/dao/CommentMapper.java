package com.cskaoyan.hackernews2.dao;

import com.cskaoyan.hackernews2.bean.Comment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CommentMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);



    Comment selectByPrimaryKey(Integer id);

  
    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectComments();

    List<Comment> selectByNewsId(@Param("newsId") int newsId);
}