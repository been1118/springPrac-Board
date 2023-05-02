package com.example.springprac2jwt.controller;


import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.LoginRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.dto.SignupRequestDto;
import com.example.springprac2jwt.dto.TokenDto;
import com.example.springprac2jwt.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "UserController", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;
    //회원가입
    @PostMapping("/signup")
    public ResponseDto<?> signup(@Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }
    //로그인
    @PostMapping("/login")
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
    //로그아웃
    @PostMapping("/logout")
    public ResponseDto<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request){
        return userService.logout(userDetails.getUser(), request);
    }
    //회원탈퇴
    @DeleteMapping("/quit")
    public ResponseDto<?> quit(LoginRequestDto loginRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.quit(loginRequestDto, userDetails.getUser());
    }
}
