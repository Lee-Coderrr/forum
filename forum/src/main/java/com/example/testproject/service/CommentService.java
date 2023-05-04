package com.example.testproject.service;

import java.util.List;
// import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testproject.dto.CommentDto;
import com.example.testproject.entity.Article;
import com.example.testproject.entity.Comment;
import com.example.testproject.repository.ArticleRepository;
import com.example.testproject.repository.CommentRepository;

import jakarta.transaction.Transactional;

/* Service -> DTO 반환 */

@Service
public class CommentService {

    /* Repo 생성 */
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ArticleRepository articleRepo;

    public List<CommentDto> comments(Long articleId) {

        return commentRepo.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());

    }

    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외 발생
        Article article = articleRepo.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));
        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepo.save(comment);

        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    /* 댓글 수정 */
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        // 댓글 수정
        target.patch(dto);
        // DB로 갱신
        Comment updated = commentRepo.save(target);
        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    /* 댓글 삭제 */
    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회(및 예외 발생)
        Comment target = commentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));
        // 댓글 삭제
        commentRepo.delete(target);
        // 삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}
