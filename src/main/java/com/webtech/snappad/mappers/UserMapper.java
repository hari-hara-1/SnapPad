package com.webtech.snappad.mappers;

import com.webtech.snappad.dtos.user.UserResponseDto;
import com.webtech.snappad.entities.User;

public class UserMapper {

    private UserMapper() {

    }

    public static UserResponseDto toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDto dto = new UserResponseDto();
        dto.setUserid(user.getUserid());
        dto.setUsername(user.getUsername());
        return dto;
    }
}

