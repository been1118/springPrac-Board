package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.PostRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto);

        post.addUser(user);
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
    public ResponseDto<?> updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = getPostIfExists(id);

        if (post.getUser().getUsername().equals(user.getUsername()) || user.getRole() == user.getRole().ADMIN) {
            post.update(postRequestDto);
            return ResponseDto.setSuccess(post);
        } else {
            return ResponseDto.set(false, 403, "수정할 권한이 없음");
        }

    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, User user) {
        Post post = getPostIfExists(id);

        if (post.getUser().getUsername().equals(user.getUsername()) || user.getRole() == user.getRole().ADMIN) {
            postRepository.deleteById(id);
            return ResponseDto.setSuccess(id);
        } else {
            return ResponseDto.set(false, 403, "삭제할 권한이 없음");
        }
    }

    protected Post getPostIfExists(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        return post;
    }


}
