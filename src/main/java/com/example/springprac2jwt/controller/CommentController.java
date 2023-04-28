package com.example.springprac2jwt.controller;


import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.CommentRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.HTTPTokener;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(value = "CommentController", description = "댓글 관련 API")
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/api/comment/{id}")
    @ApiOperation(value = "댓글 작성", notes = "댓글 작성 설명")
    public ResponseDto<?> createComment (@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id,commentRequestDto, userDetails.getUser());
    }

    //댓글 수정
    @PutMapping("/api/comment/{id}")
    @ApiOperation(value = "댓글 수정", notes = "댓글 수정 설명")
    public ResponseDto<?> updateComment (@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, commentRequestDto, userDetails.getUser());
    }

    //댓글 삭제
    @DeleteMapping("/api/comment/{id}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제 설명")
    public ResponseDto<?> deleteComment (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }
}
