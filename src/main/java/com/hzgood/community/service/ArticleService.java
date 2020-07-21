package com.hzgood.community.service;

import com.hzgood.community.mapper.ArticleMapper;
import com.hzgood.community.model.Article;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleService {
    @Resource
    protected ArticleMapper articleMapper;

    public void publish(Article article) {
        articleMapper.insert(article);
    }
}
