package com.example.springprac2jwt.controller;;

import com.example.springprac2jwt.dto.PostRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    @PostMapping("/api/posts")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request){
        return postService.createPost(requestDto, request);
    }

    @GetMapping("/api/posts")
    public ResponseDto<?> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/api/posts/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request){
        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, HttpServletRequest request){
        return postService.deletePost(id, request);
    }
}

