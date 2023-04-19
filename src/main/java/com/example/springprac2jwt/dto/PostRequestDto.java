package com.example.springprac2jwt.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostRequestDto {

    private String title;
    private String username;
    private String post;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
