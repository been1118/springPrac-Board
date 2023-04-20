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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final JwtUtil jwtUtil;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


//    private final CommentRequestDto commentRequestDto;

    @Transactional
    public ResponseDto<?> createComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token == null) return ResponseDto.set(false, 401, "토큰이 존재하지 않음");
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return ResponseDto.set(false, 403, "토큰 에러");
        }

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("게시글이 존재하지 않습니다.")
        );
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto);

        post.addComment(comment);
        comment.setUser(user);

        commentRepository.save(comment);
        return ResponseDto.setSuccess(comment);
    }

    @Transactional
    public ResponseDto<?> updateComment(Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token == null) return ResponseDto.set(false, 401, "토큰이 존재하지 않음");
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return ResponseDto.set(false, 403, "토큰 에러");
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NoSuchElementException("게시글이 존재하지 않습니다.")
        );
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        /************관리자 권한 *********************/
        if(user.getRole() == user.getRole().ADMIN) {
            comment.updateComment(commentRequestDto);
            return ResponseDto.setSuccess(comment);
        }
        /*****************************************/

        if(comment.getUser() != user ){
            return ResponseDto.set(false, 403, "수정할 권한이 없음");
        }



        comment.updateComment(commentRequestDto);
        return ResponseDto.setSuccess(comment);
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token == null) return ResponseDto.set(false, 401, "토큰이 존재하지 않음");
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return ResponseDto.set(false, 403, "토큰 에러");
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NoSuchElementException("댓글이 존재하지 않습니다.")
        );
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        /************관리자 권한 *********************/
        if(user.getRole() == user.getRole().ADMIN) {
            commentRepository.deleteById(commentId);
            return ResponseDto.setSuccess(commentId);
        }
        /*****************************************/

        if(comment.getUser() != user ){
            return ResponseDto.set(false, 403, "삭제할 권한이 없음");
        }
        commentRepository.deleteById(commentId);
        return ResponseDto.setSuccess(commentId);
    }
}

