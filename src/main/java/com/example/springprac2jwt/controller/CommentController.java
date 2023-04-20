package com.example.springprac2jwt.controller;


import com.example.springprac2jwt.dto.CommentRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.json.HTTPTokener;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}")
    public ResponseDto<?> createComment (@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(id, commentRequestDto, request);
    }

    @PutMapping("/{id}")
    public ResponseDto<?> updateComment (@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(id, commentRequestDto, request);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteComment (@PathVariable Long id, HttpServletRequest request){
        return commentService.deleteComment(id, request);
    }
}
