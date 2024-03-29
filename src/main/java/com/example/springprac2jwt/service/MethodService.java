package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.TokenDto;
import com.example.springprac2jwt.entity.*;
import com.example.springprac2jwt.exception.CustomException;
import com.example.springprac2jwt.jwt.JwtUtil;
import com.example.springprac2jwt.repository.CommentRepository;
import com.example.springprac2jwt.repository.CommentLikeRepository;
import com.example.springprac2jwt.repository.PostLikeRepository;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.example.springprac2jwt.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.example.springprac2jwt.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MethodService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;

    //게시글 존재여부 확인
    protected Post getPostIfExists(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return post;
    }
    //댓글 존재여부 확인
    protected Comment checkComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        return comment;
    }

    //게시글 좋아요 여부 확인
    protected boolean postLikeCheck(User user, Post post) {
        Optional<PostLikes> like = postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId());
        if(like.isPresent()) {
            return true;
        }
        return false;
    }
    //댓글 좋아요 여부 확인
    protected boolean commentLikeCheck(User user, Comment comment) {
        Optional<CommentLikes> like = commentLikeRepository.findByUserIdAndCommentId(user.getId(), comment.getId());
        if(like.isPresent()) {
            return true;
        }
        return false;
    }
    //헤더에 토큰 추가
    protected void setHeader(HttpServletResponse response, TokenDto tokenDto){
        response.addHeader(JwtUtil.ACCESS_KEY, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_KEY, tokenDto.getRefreshToken());
    }
}
