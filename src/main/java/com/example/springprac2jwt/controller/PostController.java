package com.example.springprac2jwt.controller;

import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.PostRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

;


@RequiredArgsConstructor
@RestController
//@Api(value = "PostController", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;
    //게시글 작성
    @PostMapping("/api/posts")
//    @ApiOperation(value = "게시글 작성", notes = "게시글 작성 설명")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(requestDto, userDetails.getUser());
    }
    //게시글 수정
    @PutMapping("/api/posts/{id}")
//    @ApiOperation(value = "게시글 수정", notes = "게시글 수정 설명")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }
    //게시글 삭제
    @DeleteMapping("/api/posts/{id}")
//    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제 설명")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id, userDetails.getUser());
    }
}

