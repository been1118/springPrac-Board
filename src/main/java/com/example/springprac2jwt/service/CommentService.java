package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.CommentRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Comment;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.jwt.JwtUtil;
import com.example.springprac2jwt.repository.CommentRepository;
import com.example.springprac2jwt.repository.PostRepository;
import com.example.springprac2jwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, User user){
        Post post = postService.getPostIfExists(commentRequestDto.getPost_id());
        Comment comment = new Comment(user, post, commentRequestDto);

        List<Comment> commentList = post.getCommentList();
        commentList.add(comment);
        post.addComment(commentList);

        commentRepository.save(comment);
        postRepository.save(post);
        return ResponseDto.setSuccess(comment);
    }

    @Transactional
    public ResponseDto<?> updateComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Comment comment = checkComment(id);

        if(comment.getUser() == user || user.getRole() == user.getRole().ADMIN){
            comment.updateComment(commentRequestDto);
            return ResponseDto.setSuccess(comment);
        }else{
        return ResponseDto.set(false, 403, "수정할 권한이 없음");
        }
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long id, User user) {
        Comment comment = checkComment(id);

        if (comment.getUser() == user || user.getRole() == user.getRole().ADMIN) {
            commentRepository.deleteById(id);
            return ResponseDto.setSuccess(id);

        } else {
            return ResponseDto.set(false, 403, "삭제할 권한이 없음");
        }
    }
    private Comment checkComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("게시글이 존재하지 않습니다.")
        );
        return comment;
    }
}

