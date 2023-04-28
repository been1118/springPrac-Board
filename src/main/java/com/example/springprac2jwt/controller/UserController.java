package com.example.springprac2jwt.controller;


import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.LoginRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.dto.SignupRequestDto;
import com.example.springprac2jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    //회원가입
    @PostMapping("/signup")
    public ResponseDto<?> signup(@Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }
    //로그인
    @ResponseBody
    @PostMapping("/login")
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
    //회원탈퇴
    @DeleteMapping("/quit")
    public ResponseDto<?> quit(LoginRequestDto loginRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.quit(loginRequestDto, userDetails.getUser());
    }
}
