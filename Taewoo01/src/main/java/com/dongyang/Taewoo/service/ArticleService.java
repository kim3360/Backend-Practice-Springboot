package com.dongyang.Taewoo.service;

import com.dongyang.Taewoo.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article create(Article article);
    Optional<Article> findById(Long id);
    List<Article> findAll();
    Article update(Long id, Article article);
    void delete(Long id);
}
