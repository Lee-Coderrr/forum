package com.example.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.testproject.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

        /* Article Id를 통한 모든 Comment 조회Comment */
        @Query(value = "SELECT * " +
                        "FROM comment " +
                        "WHERE article_id = :articleId", nativeQuery = true)
        List<Comment> findByArticleId(@Param("articleId") Long articleId);

        /* Nickname을 통해 Comment의 모든 Comment 조회 */
        @Query(name = "Comment.findByNickname")
        List<Comment> findByNickname(@Param("nickname") String nickname);

        /* 이름이 NULL */

        // @Query(value =
        // "SELECT * " +
        // "FROM comment " +
        // "WHERE article_id = :articleId",
        // nativeQuery = true)
        // List<Comment> findByNull(@Param("nil") String nil);

        /* 이름이 "" */

        // List<Comment> findByNone(@Param("none") String none);

        /* 이름에 특정 단어 포함 */

        @Query(value = "SELECT * " +
                        "FROM comment " +
                        "WHERE nickname LIKE %:ch%", nativeQuery = true)
        List<Comment> findByChar(@Param("ch") String ch);
}