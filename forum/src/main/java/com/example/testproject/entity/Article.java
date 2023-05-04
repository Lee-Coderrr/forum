package com.example.testproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public Long getId() {
        return id;
    }

    public void patch(Article article) {
        if (article.title != null) {
            this.title = article.title;
        }

        if (article.content != null) {
            this.content = article.content;
        }
    }

}
