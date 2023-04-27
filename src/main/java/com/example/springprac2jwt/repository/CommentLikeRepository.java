package com.example.springprac2jwt.repository;

import com.example.springprac2jwt.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikes, Long> {
    Optional<CommentLikes> findByUserIdAndCommentId(Long userId, Long commentId); //좋아요 확인용
    void deleteByUserIdAndCommentId(Long userId, Long commentId); //좋아요 취소
    long countByCommentId(Long commentId); //좋아요 갯수 카운트
}

