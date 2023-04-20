package com.example.springprac2jwt.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {

    @NotNull
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @NotNull
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
