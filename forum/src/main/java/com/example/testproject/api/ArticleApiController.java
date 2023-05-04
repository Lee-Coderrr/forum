package com.example.testproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.testproject.dto.ArticleDto;
import com.example.testproject.entity.Article;
import com.example.testproject.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ArticleApiController {

    @Autowired
    private ArticleService articleService;

    // Get
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("api/articles/{id}")
    public Article index(@PathVariable Long id) {
        return articleService.show(id);
    }

    // Post
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleDto articleDto) {
        // Article article = articleDto.toEntity();
        Article create = articleService.create(articleDto);
        log.info("create: {}", create.toString());
        return (create != null) ? ResponseEntity.status(HttpStatus.OK).body(create)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleDto dto) {
        Article updated = articleService.update(id, dto);
        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Delete
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {

        Article target = articleService.delete(id);

        return (target != null) ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // transaction
    @PostMapping("/api/articles/transaction-test")
    public ResponseEntity<List<Article>> createArticles(@RequestBody List<ArticleDto> dtos) {

        List<Article> created = articleService.createArticles(dtos);

        if (created == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

}
