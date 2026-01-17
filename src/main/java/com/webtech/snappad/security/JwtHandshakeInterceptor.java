package com.webtech.snappad.security;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.webtech.snappad.entities.User;
import com.webtech.snappad.services.UserService;


@Component
public class JwtHandshakeInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtHandshakeInterceptor(
            JwtUtil jwtUtil,
            UserService userService
    ) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(message);

        // This runs ONLY when client CONNECTS
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            List<String> authHeaders =
                    accessor.getNativeHeader("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
                throw new RuntimeException("Missing Authorization header");
            }

            String token = authHeaders
                    .get(0)
                    .replace("Bearer ", "");

            Long userId = jwtUtil.extractUserId(token);
            User user = userService.getById(userId);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            List.of()
                    );

            // Attach user to WebSocket session
            accessor.setUser(authentication);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        }

        return message;
    }
}

