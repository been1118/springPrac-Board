package com.example.springprac2jwt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 클래스에서 발생하는 예외 핸들러
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    // Valid 예외 핸들러
    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorResponse> handleBindException(BindException  ex) {
        BindingResult bindingResult = ex.getBindingResult();

        StringBuilder sb = new StringBuilder();
        for ( FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage());
        }
        return ErrorResponse.toResponseEntityValid(sb.toString(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e){
        return ErrorResponse.toResponseEntityValid(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e){
        return ErrorResponse.toResponseEntity(ErrorCode.valueOf(e.getMessage()));
    }
}