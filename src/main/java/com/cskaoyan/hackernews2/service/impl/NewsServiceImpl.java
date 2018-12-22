package com.cskaoyan.hackernews2.service.impl;

import com.cskaoyan.hackernews2.bean.News;
import com.cskaoyan.hackernews2.dao.NewsMapper;
import com.cskaoyan.hackernews2.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;

    @Override
    public int addnews(News news) {
        int insert = newsMapper.insert(news);
        return insert;

    }

    @Override
    public List<News> selectNews() {
        List<News> newsList= newsMapper.selectNews();
        return newsList;
    }

    @Override
    public int likeCountById(String newsId) {
        int id = Integer.parseInt(newsId);
        int like = newsMapper.selectLikeCountById(id);
        int newlike=like+1;
        int j=newsMapper.likeCountById(newlike,id);
        return j;
    }

    @Override
    public int dislikeCountById(String newsId) {
        int id = Integer.parseInt(newsId);
        int like = newsMapper.selectLikeCountById(id);
        int newlike=like-1;
        int j=newsMapper.likeCountById(newlike,id);
        return j;
    }

    @Override
    public News queryNewById(String value) {
        int i = Integer.parseInt(value);
        News news = newsMapper.selectByPrimaryKey(i);
        return news;
    }

    @Override
    @Transactional
    public void updateCommentCount(String newsId) {
        int i = Integer.parseInt(newsId);
        int commentCountById = newsMapper.selectCommentCountById(i);
        int commentCount=commentCountById+1;
        newsMapper.updateCommentCountByid(i,commentCount);
    }

    @Override
    public List<News> getLatestNews(int offset, int limit) {

        return  newsMapper.getLatestNews(offset,limit);
    }
}
