package com.example.testproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testproject.dto.ArticleDto;
import com.example.testproject.entity.Article;
import com.example.testproject.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepo;

    public List<Article> index() {
        return articleRepo.findAll();
    }

    public Article show(Long id) {
        return articleRepo.findById(id).orElse(null);
    }

    public Article create(ArticleDto articleDto) {
        Article target = articleDto.toEntity();

        if (target.getId() != null)
            return null;

        return articleRepo.save(target);
    }

    public Article update(Long id, ArticleDto dto) {

        Article article = dto.toEntity();

        log.info("id: {}, article: {}", id, article.toString());

        Article target = articleRepo.findById(id).orElse(null);

        if (target == null || id != article.getId()) {

            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        target.patch(article);

        Article updated = articleRepo.save(target);

        return updated;
    }

    public Article delete(Long id) {

        Article target = articleRepo.findById(id).orElse(null);

        if (target == null) {
            log.info("잘못된 삭제, Article: {}");
            return null;
        }

        articleRepo.delete(target);

        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleDto> dtos) {

        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        articleList.stream()
                .forEach(article -> articleRepo.save(article));

        // 강제 예외 발생
        articleRepo.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!"));
        // 결과값 반환
        return articleList;

    }

}
