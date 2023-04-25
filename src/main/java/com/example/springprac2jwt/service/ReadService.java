package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.exception.CustomException;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.springprac2jwt.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReadService {

    private final PostRepository postRepository;

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> readPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return ResponseDto.setSuccess(postList);
    }
    //게시글 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> readPost(Long id) {
        return ResponseDto.setSuccess(getPostIfExists(id));
    }

    private Post getPostIfExists(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return post;
    }
}
