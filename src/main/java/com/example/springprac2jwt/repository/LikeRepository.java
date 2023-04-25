package com.example.springprac2jwt.repository;

import com.example.springprac2jwt.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndPostIdAndCommentId(Long userId, Long postId, Long commentId); //좋아요 확인용
    void deleteByUserIdAndPostIdAndCommentId(Long userId, Long postId, Long commentId); //좋아요 취소
    long countByPostIdAndCommentId(Long postId, Long commentId); //좋아요 갯수 카운트
}

