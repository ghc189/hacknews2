package com.cskaoyan.hackernews2.service.impl;

import com.cskaoyan.hackernews2.bean.Msg;
import com.cskaoyan.hackernews2.dao.MsgMapper;
import com.cskaoyan.hackernews2.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {
@Autowired
    MsgMapper msgMapper;

    @Override
    public List<Msg> selectMsgByUid(Integer uid) {

       List<Msg> msgList= msgMapper.selectByUid(uid);
        return msgList;
    }

    @Override
    public List<Msg> findbyconversationId(String conversationId) {
        List<Msg> msgList=  msgMapper.selectByconversationId(conversationId);
        return msgList;
    }

    @Override
    public void addMsg(Msg msg) {
    msgMapper.insert(msg);
    }

    @Override
    public List<Msg> selectMsgByFromid(Integer id) {

        return  msgMapper.selectMsgByFromid(id);
    }
}
