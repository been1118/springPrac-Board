package com.example.springprac2jwt.controller;


import com.example.springprac2jwt.Security.UserDetailsImpl;
import com.example.springprac2jwt.dto.LoginRequestDto;
import com.example.springprac2jwt.dto.ResponseDto;
import com.example.springprac2jwt.dto.SignupRequestDto;
import com.example.springprac2jwt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
//@Api(value = "UserController", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;
    //회원가입
    @PostMapping("/signup")
//    @ApiOperation(value = "회원가입", notes = "회원가입 설명")
    public ResponseDto<?> signup(@Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }
    //로그인
    @ResponseBody
    @PostMapping("/login")
//    @ApiOperation(value = "로그인", notes = "로그인 설명")
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
    //로그아웃
    @PostMapping("/logout")
//    @ApiOperation(value = "로그아웃", notes = "로그아웃 설명")
    public ResponseDto<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.logout(userDetails.getUser());
    }
    //회원탈퇴
    @DeleteMapping("/quit")
//    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴 설명")
    public ResponseDto<?> quit(LoginRequestDto loginRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.quit(loginRequestDto, userDetails.getUser());
    }
}
