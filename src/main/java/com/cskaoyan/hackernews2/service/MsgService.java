package com.cskaoyan.hackernews2.service;

import com.cskaoyan.hackernews2.bean.Msg;

import java.util.List;

public interface MsgService {
    List<Msg> selectMsgByUid(Integer uid);

    List<Msg> findbyconversationId(String conversationId);

    void addMsg(Msg msg);

    List<Msg> selectMsgByFromid(Integer id);
}
