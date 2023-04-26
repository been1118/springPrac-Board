package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Comment;
import com.example.springprac2jwt.entity.Likes;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MethodService methodService;

    //게시글 좋아요
    @Transactional
    public ResponseDto<?> Likes(Long postId, User user){
        Post post = methodService.getPostIfExists(postId);
        if(methodService.postLikeCheck(user, post, null)){//좋아요가 있으면 DB에서 삭제, 없으면 생성
            likeRepository.deleteByUserIdAndPostIdAndCommentId(user.getId(), postId, null);
        } else {
            Likes like = new Likes(user, post, null);
            likeRepository.save(like);
        }
        long likesCheck = likeRepository.countByPostIdAndCommentId(postId, null);
        post.checkLikes(likesCheck);
        return ResponseDto.setSuccess(likesCheck);
    }

    //댓글 좋아요
    @Transactional
    public ResponseDto<?> Likes(Long postId, Long commentId, User user) {
        Post post = methodService.getPostIfExists(postId);
        Comment comment =  methodService.checkComment(commentId);
        if(methodService.commentLikeCheck(user, post, comment))  {//좋아요가 있으면 DB에서 삭제, 없으면 생성
           likeRepository.deleteByUserIdAndPostIdAndCommentId(user.getId(), postId, commentId);
        } else {
           Likes like = new Likes(user, post,  comment);
           likeRepository.save(like);
        }
        long likesCheck = likeRepository.countByPostIdAndCommentId(postId, commentId);
        comment.checkLikes(likesCheck);
        return ResponseDto.setSuccess(likesCheck);
    }
}
