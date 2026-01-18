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

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtStompChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("STOMP COMMAND = " + accessor.getCommand());
        System.out.println("STOMP HEADERS = " + accessor.toNativeHeaderMap());

        // 🔐 Authenticate ONLY on CONNECT
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            List<String> authHeaders =
                    accessor.getNativeHeader("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
                throw new RuntimeException("Missing Authorization header in STOMP CONNECT");
            }

            String token = authHeaders.get(0);

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Long userId = jwtUtil.extractUserId(token);
            User user = userService.getById(userId);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            List.of() // no roles for now
                    );

            // 🔥 THIS IS THE KEY PART
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            accessor.setUser(authentication);
        }

        return message;
    }
}
