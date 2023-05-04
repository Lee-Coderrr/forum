package com.example.testproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.testproject.entity.Article;
import com.example.testproject.entity.Comment;


@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepo;

    @Test
    @DisplayName("특정 articleID의 모든 댓글 조회")
    void testFindByArticleId() {

        Long id = 4L;

        // Real Data
        List<Comment> comment = commentRepo.findByArticleId(id);

        // Test Data

        Article article = new Article(id, "당신의 인생 영화는?", "댓글 ㄱ");

        Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅");
        Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
        Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");
        List<Comment> expected = Arrays.asList(a, b, c);

        // Vertification
        assertEquals(comment.toString(), expected.toString());

    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            // 준비
            String nickname = "Park";
            // 수행
            List<Comment> comments = commentRepo.findByNickname(nickname);
            // 예상
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "굳 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c);
            // 검증

            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }

        /* Case 2: Kim의 모든 댓글 조회 */
        {
            // 준비
            String nickname = "Kim";
            // 수행
            List<Comment> comments = commentRepo.findByNickname(nickname);
            // 예상
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }
    }

    @Test
    void testFindByChar() {
        {
            String ch = "i";

            // Real Data
            List<Comment> comments = commentRepo.findByChar(ch);

            // Test Data
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Kim", "아이 엠 샘");
            Comment b = new Comment(3L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Choi", "쇼생크의 탈출");
            Comment c = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Kim", "샤브샤브");
            Comment d = new Comment(6L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Choi", "초밥");
            Comment e = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "Kim", "유튜브");
            Comment f = new Comment(9L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "Choi", "독서");

            List<Comment> expected = Arrays.asList(a, b, c, d, e, f);

            // Vertify
            assertEquals(expected.toString(), comments.toString());

        }
    }
}