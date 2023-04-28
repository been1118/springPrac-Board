package com.example.springprac2jwt.controller;

import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Api(value = "LikeController", description = "좋아요 관련 API")
public class LikeController {

    private final LikeService likesService;

    //게시글 좋아요
    @PostMapping("postLikes/{postId}")
    @ApiOperation(value = "게시글 좋아요 기능", notes = "게시글 좋아요 기능 설명")
    public ResponseDto<?> postLikes(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likesService.postLikes(postId, userDetails.getUser());
    }



    //댓글 좋아요
    @PostMapping("commentLikes/{commentId}")
    @ApiOperation(value = "댓글 좋아요 기능", notes = "댓글 좋아요 기능 설명")
    public ResponseDto<?> commentLikes(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likesService.commentLikes(commentId, userDetails.getUser());
    }
}
