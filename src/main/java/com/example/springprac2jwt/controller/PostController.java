package com.example.springprac2jwt.controller;

import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.PostRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.exception.CustomException;
import com.example.springprac2jwt.exception.ErrorCode;
import com.example.springprac2jwt.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

;


@RequiredArgsConstructor
@RestController
@Tag(name = "PostController", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;
    //게시글 작성
    @PostMapping("/api/posts")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(requestDto, userDetails.getUser());
    }
    //게시글 수정
    @PutMapping("/api/posts/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }
    //게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id, userDetails.getUser());
    }

}

