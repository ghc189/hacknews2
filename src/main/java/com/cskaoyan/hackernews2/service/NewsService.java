package com.cskaoyan.hackernews2.service;

import com.cskaoyan.hackernews2.bean.News;

import java.util.List;

public interface NewsService {
    int  addnews(News news);

    List<News> selectNews();

    int likeCountById(String newsId);

    int dislikeCountById(String newsId);

    News queryNewById(String value);

    void updateCommentCount(String newsId);

    List<News> getLatestNews(int offset, int limit);
}
