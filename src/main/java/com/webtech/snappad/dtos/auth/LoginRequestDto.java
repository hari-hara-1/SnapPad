package com.webtech.snappad.dtos.auth;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
