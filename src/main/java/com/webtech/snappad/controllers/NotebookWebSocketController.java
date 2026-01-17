package com.webtech.snappad.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.webtech.snappad.dtos.websocket.AutosaveRequestDto;
import com.webtech.snappad.entities.User;
import com.webtech.snappad.services.NotebookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotebookWebSocketController {

    private final NotebookService notebookService;

    /**
     * Client sends message to:
     *   /app/notebook/autosave
     */
    @MessageMapping("/notebook/autosave")
    public void autosave(@Payload AutosaveRequestDto dto) {

        // 🔐 User already authenticated by JWT during WebSocket handshake
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = user.getUserid();

        // 🔁 Reuse your EXISTING service method (unchanged)
        notebookService.autosave(
                userId,
                dto.getNotebookId(),
                dto.getContent()
        );
    }
}

