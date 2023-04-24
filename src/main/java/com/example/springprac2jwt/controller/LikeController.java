package com.example.springprac2jwt.controller;

import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class LikeController {

    private final LikeService likesService;

    //게시글 좋아요
    @PostMapping("like/{postId}")
    public ResponseDto<?> postLikes(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likesService.Likes(postId, userDetails.getUser());
    }



    //댓글좋아요
    @PostMapping("like/{postId}/{commentId}")
    public ResponseDto<?> commentLikes(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likesService.Likes(postId, commentId, userDetails.getUser());
    }
}