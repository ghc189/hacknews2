package com.cskaoyan.hackernews2.bean;

import java.util.Date;
import java.util.List;

public class Conversation {
    Integer unread;
    User user;
    List<Msg> conversation;
        String conversationid;
    Date createddate;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getConversationid() {
        return conversationid;
    }

    public void setConversationid(String conversationid) {
        this.conversationid = conversationid;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Msg> getConversation() {
        return conversation;
    }

    public void setConversation(List<Msg> conversation) {
        this.conversation = conversation;
    }
}
