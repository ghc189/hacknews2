package com.cskaoyan.hackernews2.dao;

import com.cskaoyan.hackernews2.bean.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    List<News> selectNews();

    int likeCountById(@Param("likeCount") int like,@Param("id") int id);

    @Select("select likeCount from `news` where id=#{id}")
    int selectLikeCountById(@Param("id") int id);

    void updateCommentCountByid(@Param("id") int id,@Param("commentCount") int commentCount);

    @Select("select commentCount from `news` where id=#{id}")
    int selectCommentCountById(@Param("id") int id);

    int selectownerIdByNid(@Param("newsId") int newsId);

    List<News> getLatestNews(@Param("offset") int offset,@Param("limit") int limit);
}