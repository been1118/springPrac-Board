package com.example.springprac2jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    POST_NOT_FOUND(BAD_REQUEST, "게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(BAD_REQUEST, "해당 댓글이 존재하지 않습니다."),

    NOT_MATCH_ADMIN_TOKEN(BAD_REQUEST, "관리자 암호가 틀려 등록이 불가능합니다."),
    EXIST_USERNAME(BAD_REQUEST, "중복된 username 입니다."),

    CANNOT_FOUND_USER(BAD_REQUEST, "회원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
