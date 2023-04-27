package com.example.springprac2jwt.repository;

import com.example.springprac2jwt.entity.CommentLikes;
import com.example.springprac2jwt.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLikes, Long> {

    Optional<CommentLikes> findByUserIdAndPostId(Long userId, Long postId); //좋아요 확인용
    void deleteByUserIdAndPostId(Long userId, Long postId); //좋아요 취소
    long countByPostId(Long postId); //좋아요 갯수 카운트
}
