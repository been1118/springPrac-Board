package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.PostRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
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
    public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto) {
        Post post = getPostIfExists(id);
        checkPassword(post, requestDto.getPassword());
        post.update(requestDto);
        return ResponseDto.setSuccess(post);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, PostRequestDto requestDto) {
        Post post = getPostIfExists(id);
        checkPassword(post, requestDto.getPassword());
        postRepository.deleteById(id);
        return ResponseDto.setSuccess(post);
    }



    private Post getPostIfExists(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    private void checkPassword(Post post, String password) {
        if (!post.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}
