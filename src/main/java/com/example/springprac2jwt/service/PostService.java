package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.PostRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.jwt.JwtUtil;
import com.example.springprac2jwt.repository.PostRepository;
import com.example.springprac2jwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token == null) return ResponseDto.set(false, 401, "토큰이 존재하지 않음");
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return ResponseDto.set(false, 403, "토큰 에러");
        }
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
        return ResponseDto.setSuccess(post);

    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return ResponseDto.setSuccess(postList);
    }
    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        return ResponseDto.setSuccess(getPostIfExists(id));
    }

    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        Post post = getPostIfExists(id);
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token == null) return ResponseDto.set(false, 401, "토큰이 존재하지 않음");
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return ResponseDto.set(false, 403, "토큰 에러");
        }
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        if (post.getUser().getUsername().equals(claims.getSubject())) {
            post.update(postRequestDto);
        } else return ResponseDto.set(false, 403, "수정할 권한이 없음");
        return ResponseDto.setSuccess(post);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
        Post post = getPostIfExists(id);
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token == null) return ResponseDto.set(false, 401, "토큰이 존재하지 않음");
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            return ResponseDto.set(false, 403, "토큰 에러");
        }
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        if (post.getUser().getUsername().equals(claims.getSubject())) {
            postRepository.deleteById(id);
        } else return ResponseDto.set(false, 403, "삭제할 수 없습니다.");
        return ResponseDto.setSuccess(post);
    }



    private Post getPostIfExists(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }


}
