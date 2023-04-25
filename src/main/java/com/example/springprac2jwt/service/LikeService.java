package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Comment;
import com.example.springprac2jwt.entity.Likes;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.exception.CustomException;
import com.example.springprac2jwt.repository.CommentRepository;
import com.example.springprac2jwt.repository.LikeRepository;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.example.springprac2jwt.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.example.springprac2jwt.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    //게시글 좋아요
    @Transactional
    public ResponseDto<?> Likes(Long postId, User user){
        Post post = getPostIfExists(postId);
        if(postLikeCheck(user, post, null)){//좋아요가 있으면 DB에서 삭제, 없으면 생성
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
        Post post = getPostIfExists(postId);
        Comment comment =  checkComment(commentId);
        if(commentLikeCheck(user, post, comment))  {//좋아요가 있으면 DB에서 삭제, 없으면 생성
           likeRepository.deleteByUserIdAndPostIdAndCommentId(user.getId(), postId, commentId);
        } else {
           Likes like = new Likes(user, post,  comment);
           likeRepository.save(like);
        }
        long likesCheck = likeRepository.countByPostIdAndCommentId(postId, commentId);
        comment.checkLikes(likesCheck);
        return ResponseDto.setSuccess(likesCheck);
    }

    private Post getPostIfExists(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return post;
    }
    //댓글 존재여부 확인
    private Comment checkComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        return comment;
    }

    //게시글 좋아요 여부 확인
    private boolean postLikeCheck(User user, Post post, Comment comment) {
        Optional<Likes> like = likeRepository.findByUserIdAndPostIdAndCommentId(user.getId(), post.getId(), null);
        if(like.isPresent()) {
            return true;
        }
        return false;
    }
    //댓글 좋아요 여부 확인
    private boolean commentLikeCheck(User user, Post post, Comment comment) {
        Optional<Likes> like = likeRepository.findByUserIdAndPostIdAndCommentId(user.getId(), post.getId(), comment.getId());
        if(like.isPresent()) {
            return true;
        }
        return false;
    }
}
