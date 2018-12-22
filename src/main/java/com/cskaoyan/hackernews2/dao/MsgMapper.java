package com.cskaoyan.hackernews2.dao;

import com.cskaoyan.hackernews2.bean.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MsgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKey(Msg record);

    List<Msg> selectByUid(@Param("uid") Integer uid);

    List<Msg> selectByconversationId(@Param("conversationId") String conversationId);

    List<Msg> selectMsgByFromid(@Param("Fromid") Integer id);
}