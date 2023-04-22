package com.example.springprac2jwt.controller;


import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.CommentRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.json.HTTPTokener;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseDto<?> createComment (@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/api/comment/{id}")
    public ResponseDto<?> updateComment (@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseDto<?> deleteComment (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }
}
