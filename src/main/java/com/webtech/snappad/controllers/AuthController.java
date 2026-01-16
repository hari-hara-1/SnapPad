package com.webtech.snappad.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webtech.snappad.dtos.auth.LoginRequestDto;
import com.webtech.snappad.dtos.auth.LoginResponseDto;
import com.webtech.snappad.entities.User;
import com.webtech.snappad.security.JwtUtil;
import com.webtech.snappad.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request
    ) {
        User user = userService.getByUsername(request.getUsername());

        if (!userService.matchesPassword(
                request.getPassword(),
                user.getPassword()
        )) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
