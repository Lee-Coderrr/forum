package com.example.testproject.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.testproject.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Override
    ArrayList<Article> findAll();

}
