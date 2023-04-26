package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.CommentRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Comment;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.exception.CustomException;
import com.example.springprac2jwt.repository.CommentRepository;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.springprac2jwt.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MethodService methodService;

    //댓글 작성
    @Transactional
    public ResponseDto<?> createComment(Long id, CommentRequestDto commentRequestDto, User user){
        Post post = methodService.getPostIfExists(id);
        Comment comment = new Comment(user, post, commentRequestDto);

        List<Comment> commentList = post.getCommentList();
        commentList.add(comment);
        post.addComment(commentList);

        commentRepository.save(comment);
        postRepository.save(post);
        return ResponseDto.setSuccess(comment);
    }
    //댓글 수정
    @Transactional
    public ResponseDto<?> updateComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Comment comment = methodService.checkComment(id);
        //유저 확인
        if(comment.getUser() == user || user.getRole() == user.getRole().ADMIN){
            comment.updateComment(commentRequestDto);
            return ResponseDto.setSuccess(comment);
        }else{
        return ResponseDto.set(false, 403, "수정할 권한이 없음");
        }
    }
    //댓글 삭제
    @Transactional
    public ResponseDto<?> deleteComment(Long id, User user) {
        Comment comment = methodService.checkComment(id);
        //유저 확인
        if (comment.getUser() == user || user.getRole() == user.getRole().ADMIN) {
            commentRepository.deleteById(id);
            return ResponseDto.setSuccess(id);
        } else {
            return ResponseDto.set(false, 403, "삭제할 권한이 없음");
        }
    }

}
