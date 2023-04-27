package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.*;
import com.example.springprac2jwt.repository.CommentLikeRepository;
import com.example.springprac2jwt.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    private final MethodService methodService;

    //게시글 좋아요
    @Transactional
    public ResponseDto<?> postLikes(Long postId, User user){
        Post post = methodService.getPostIfExists(postId);
        if(methodService.postLikeCheck(user, post)){//좋아요가 있으면 DB에서 삭제, 없으면 생성
            postLikeRepository.deleteByUserIdAndPostId(user.getId(), postId);
        } else {
            PostLikes postLikes = new PostLikes(user, post);
            postLikeRepository.save(postLikes);
        }
        long likesCheck = postLikeRepository.countByPostId(postId);
        post.checkLikes(likesCheck);
        return ResponseDto.setSuccess(likesCheck);
    }

    //댓글 좋아요
    @Transactional
    public ResponseDto<?> commentLikes(Long commentId, User user) {
        Comment comment =  methodService.checkComment(commentId);
        if(methodService.commentLikeCheck(user, comment))  {//좋아요가 있으면 DB에서 삭제, 없으면 생성
            commentLikeRepository.deleteByUserIdAndCommentId(user.getId(), commentId);
        } else {
           CommentLikes commentLikes = new CommentLikes(user, comment);
           commentLikeRepository.save(commentLikes);
        }
        long likesCheck = commentLikeRepository.countByCommentId(commentId);
        comment.checkLikes(likesCheck);
        return ResponseDto.setSuccess(likesCheck);
    }
}
