package com.example.springprac2jwt.controller;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReadController {

    private final PostService postService;

    //게시글 전체조회
    @GetMapping("/api/read")
    public ResponseDto<?> getPosts(){
        return postService.getPosts();
    }
    //게시글 상세조회
    @GetMapping("/api/read/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
}
