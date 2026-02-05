package com.webtech.snappad.controllers;

import com.webtech.snappad.dtos.user.UserCreateRequestDto;
import com.webtech.snappad.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody UserCreateRequestDto request
    ) {
        userService.createUser(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok("User created");
    }
}
