package com.example.springprac2jwt.controller;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.ReadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@Tag(name = "ReadController", description = "조회 관련 API")
public class ReadController {

    private final ReadService readService;

    //게시글 전체조회
    @GetMapping("/api/read")
    public ResponseDto<?> readPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        return readService.readPosts(page-1, size, sortBy, isAsc);
    }

    //게시글 상세조회
    @GetMapping("/api/read/{id}")
    public ResponseDto<?> readPost(@PathVariable Long id) {
        return readService.readPost(id);
    }
}
