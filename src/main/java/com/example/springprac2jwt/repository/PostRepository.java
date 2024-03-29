package com.example.springprac2jwt.repository;

import com.example.springprac2jwt.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByTitleContaining(String searchKeyword, Pageable pageable);

}
