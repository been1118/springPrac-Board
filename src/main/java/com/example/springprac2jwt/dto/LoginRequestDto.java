package com.example.springprac2jwt.dto;

import com.example.springprac2jwt.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String username;

    private String password;

}