package com.example.springprac2jwt.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class CommentRequestDto {
    private Long post_id;
    private String comment;

}
