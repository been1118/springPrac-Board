package com.example.springprac2jwt.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z]).{4,10}$", message = "아이디는 4~10자 알파벳 소문자, 숫자로 작성해주세요.")
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()_+|<>?:{}])(?=\\S+$).{8,15}$", message = "비밀번호는 8~15자 알파벳 대소문자, 숫자, 특수문자로 작성해주세요.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
