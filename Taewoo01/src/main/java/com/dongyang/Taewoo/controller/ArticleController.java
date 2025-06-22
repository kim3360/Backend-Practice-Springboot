package com.dongyang.Taewoo.controller;

import com.dongyang.Taewoo.entity.Article;
import com.dongyang.Taewoo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    // 전체 조회
    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    // ID로 조회
    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id) {
        return articleRepository.findById(id).orElseThrow();
    }

    // 카테고리별 조회
    @GetMapping("/category/{category}")
    public List<Article> getArticlesByCategory(@PathVariable String category) {
        return articleRepository.findByCategory(category);
    }

    // 등록
    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    // 수정
    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article updated) {
        Article article = articleRepository.findById(id).orElseThrow();
        article.setCategory(updated.getCategory());
        article.setQuestion(updated.getQuestion());
        article.setAnswer(updated.getAnswer());
        return articleRepository.save(article);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleRepository.deleteById(id);
    }
}
