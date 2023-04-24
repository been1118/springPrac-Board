package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.PostRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Comment;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.entity.User;
import com.example.springprac2jwt.exception.CustomException;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.springprac2jwt.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.example.springprac2jwt.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //게시글 작성
    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto);

        post.addUser(user);
        postRepository.save(post);
        return ResponseDto.setSuccess(post);
    }
    //게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return ResponseDto.setSuccess(postList);
    }
    //게시글 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        return ResponseDto.setSuccess(getPostIfExists(id));
    }

    //게시글 수정
    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = getPostIfExists(id);
        //유저 확인
        if (post.getUser().getUsername().equals(user.getUsername()) || user.getRole() == user.getRole().ADMIN) {
            post.update(postRequestDto);
            return ResponseDto.setSuccess(post);
        } else {
            return ResponseDto.set(false, 403, "수정할 권한이 없음");
        }

    }
    //게시글 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long id, User user) {
        Post post = getPostIfExists(id);
        //유저 확인
        if (post.getUser().getUsername().equals(user.getUsername()) || user.getRole() == user.getRole().ADMIN) {
            postRepository.deleteById(id);
            return ResponseDto.setSuccess(id);
        } else {
            return ResponseDto.set(false, 403, "삭제할 권한이 없음");
        }
    }
    private Post getPostIfExists(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return post;
    }


}
