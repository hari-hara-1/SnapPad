package com.webtech.snappad.dtos.user;

import lombok.Data;

@Data
public class UserCreateRequestDto {
    private String username;
    private String password;
}

