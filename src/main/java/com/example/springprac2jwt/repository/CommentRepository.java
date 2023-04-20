package com.example.springprac2jwt.repository;

import com.example.springprac2jwt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
