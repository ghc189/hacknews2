package com.cskaoyan.hackernews2.asyncevent;


/**
 * 表示不同的事件信息
 *
 */
public class Event {

    EventType eventType ;  //点赞 1 点踩 2 评论 3  分享 4 关注 5


    //谁触发的事件
    String   actorId;

    //事件的接收方
    String  ownerId;

    //点赞的具体的新闻id
    String itemId;

     //点赞的类型 0给新闻 1给评论 给其他的
    String itemType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
