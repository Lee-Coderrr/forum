package com.example.testproject.dto;

import com.example.testproject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
@ToString
public class ArticleDto {
    private Long id;
    private String title;
    private String content;


    public Article toEntity() {
        return new Article(id, title, content);
    }
}