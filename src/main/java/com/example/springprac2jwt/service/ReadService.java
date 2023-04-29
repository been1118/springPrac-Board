package com.example.springprac2jwt.service;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.entity.Post;
import com.example.springprac2jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadService {

    private final PostRepository postRepository;
    private final MethodService methodService;

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> readPosts(int page, int size, String sortBy, boolean isAsc) {
        //List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        //페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage = postRepository.findAll(pageable);

        return ResponseDto.setSuccess(postPage);
    }
    //게시글 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> readPost(Long id) {
        return ResponseDto.setSuccess(methodService.getPostIfExists(id));
    }
}

/*
package com.sparta.myselectshop.repository;

import com.sparta.myselectshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByUserId(Long userId, Pageable pageable);
    Optional<Product> findByIdAndUserId(Long id, Long userId);
    Page<Product> findAll(Pageable pageable);
}
 */