package com.example.springprac2jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    private boolean success;
    private final int statusCode;
    private D data;

    public static <D> ResponseDto setSuccess (D data) {
        return ResponseDto.set(true, 200, data);
    }
}
//리턴값을 데이터에 담아서 클라이언트로 보냄