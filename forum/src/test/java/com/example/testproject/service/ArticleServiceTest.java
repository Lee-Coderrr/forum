package com.example.testproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.testproject.dto.ArticleDto;
import com.example.testproject.entity.Article;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void testIndex_success() {
        
        Article a = new Article(1L, "aaaa", "aaaa");
        Article b = new Article(2L, "bbbb", "bbbb");
        Article c = new Article(3L, "cccc", "cccc");

        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        List<Article> articles = articleService.index();


        assertEquals(articles.toString(), expected.toString());
    }

    @Test
    void testShow_fail() {
        Long id = 1L;

        // 테스트 케이스
        Article expected = new Article(id, "aaa1111a", "aaaa");

        // 실제 케이스
        Article article = articleService.show(id);

        assertEquals(article.toString(), expected.toString());
        // 비교
    }

    @Test
    @Transactional
    void testCreate_success() {

        ArticleDto dto = new ArticleDto(null, "hhhh", "hhhh");

        Article expected = new Article(4L, "hhhh", "hhhh");

        Article article = articleService.create(dto);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void testCreate_fail() {
        ArticleDto dto = new ArticleDto(-1L, "hhhh11111", "hhhh11111");

        Article expected = new Article(4L, "hhhh", "hhhh");

        Article article = articleService.create(dto);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void testDelete_success() {

        Long id = 1L;

        Article b = new Article(2L, "bbbb", "bbbb");
        Article c = new Article(3L, "cccc", "cccc");

        // testcase
        List<Article> expected = new ArrayList<Article>(Arrays.asList(b, c));

        articleService.delete(id);
        // Real Data
        List<Article> articles = articleService.index();

        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    void testDelete_fail() {

        Long id = -1L;

        // Test Case
        Article expected = articleService.delete(1L);

        // Real Data
        Article target = articleService.delete(id);
        // Real Data

        assertEquals(expected.toString(), target);

    }

    @Test
    void testUpdate_success() {

        Long id = 3L;

        Article expected = new Article(id, "xxxx", "xxxx");

        ArticleDto dto = new ArticleDto(id, "xxxx", "xxxx");

        Article article = articleService.update(3L, dto);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void testUpdate_fail() {

        Long id = 3L;

        Article expected = new Article(id, "xxxx", "xxxx");

        ArticleDto dto = new ArticleDto(3L, "zzzz", "xxxx");

        Article article = articleService.update(3L, dto);

        assertEquals(expected.toString(), article.toString());
    }
}
