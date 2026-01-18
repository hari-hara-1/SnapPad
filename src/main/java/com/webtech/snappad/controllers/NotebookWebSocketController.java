package com.webtech.snappad.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.webtech.snappad.dtos.websocket.AutosaveRequestDto;
import com.webtech.snappad.entities.User;
import com.webtech.snappad.services.NotebookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotebookWebSocketController {

    private final NotebookService notebookService;

    @MessageMapping("/notebook/autosave")
    public void autosave(
            @Payload AutosaveRequestDto dto,
            Authentication authentication   // ✅ THIS IS THE KEY
    ) {
        System.out.println("AUTOSAVE HIT");

        if (authentication == null) {
            throw new RuntimeException("No authentication in WebSocket message");
        }

        User user = (User) authentication.getPrincipal();

        notebookService.autosave(
                user.getUserid(),
                dto.getNotebookId(),
                dto.getContent()
        );
    }
}
