package com.cskaoyan.hackernews2.service;

public interface LikeService {
    public void like(int newsId,int uid);
    public void dislike(int newsId,int uid);
    public  int getLikeCount(int newsId);
    public  int getdisLikeCount(int newsId);
    public int getLike(int newsId,int uid);
}
