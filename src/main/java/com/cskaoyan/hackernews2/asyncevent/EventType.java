package com.cskaoyan.hackernews2.asyncevent;

/**
 *  //点赞 1 点踩 2 评论 3  分享 4 关注 5
 */
public enum EventType {
    Like(1),
    DISLIKE(2),
    COMMENT(3),
    SHARE(4);

    private  int value;

    EventType(int value) {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
