package com.example.springprac2jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    private boolean success;
    private final int statusCode;
    private D data;

    public static <D> ResponseDto setSuccess (D data) {
        return ResponseDto.set(true, 200, data);
    }
}
