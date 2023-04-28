package com.example.springprac2jwt.controller;

import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.service.ReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(value = "ReadController", description = "조회 관련 API")
public class ReadController {

    private final ReadService readService;

    //게시글 전체조회
    @GetMapping("/api/read")
    @ApiOperation(value = "게시글 조회", notes = "게시글 조회 설명")
    public ResponseDto<?> readPosts(){
        return readService.readPosts();
    }
    //게시글 상세조회
    @GetMapping("/api/read/{id}")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 상세 조회 설명")
    public ResponseDto<?> readPost(@PathVariable Long id) {
        return readService.readPost(id);
    }
}
