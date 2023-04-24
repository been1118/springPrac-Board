package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Comment;
import com.example.springprac2jwt.entity.Likes;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.repository.CommentRepository;
import com.example.springprac2jwt.repository.LikeRepository;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

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

        if(postLikeCheck(user, post, null)){
            likeRepository.deleteByUserIdAndPostIdAndCommentId(user.getId(), postId, null);
        } else {
            Likes like = new Likes(user, post, null);
            likeRepository.save(like);
        }
        return ResponseDto.setSuccess(likeRepository.countByPostIdAndCommentId(postId, null));
    }

    //댓글 좋아요
    @Transactional
    public ResponseDto<?> Likes(Long postId, Long commentId, User user) {
        Post post = getPostIfExists(postId);

        Comment comment =  checkComment(commentId);

       if(commentLikeCheck(user, post, comment))  {
           likeRepository.deleteByUserIdAndPostIdAndCommentId(user.getId(), postId, commentId);
       } else {
           Likes like = new Likes(user, post,  comment);
           likeRepository.save(like);
       }
        return ResponseDto.setSuccess(likeRepository.countByPostIdAndCommentId(postId, commentId));
    }

    //게시글 존재 여부 확인
    private Post getPostIfExists(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        return post;
    }
    //댓글 존재 여부 확인
    private Comment checkComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("게시글이 존재하지 않습니다.")
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
