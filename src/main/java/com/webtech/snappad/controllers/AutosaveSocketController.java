package com.webtech.snappad.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.webtech.snappad.dtos.websocket.NoteAutosaveDto;
import com.webtech.snappad.entities.User;
import com.webtech.snappad.services.NotebookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AutosaveSocketController {

    private final NotebookService notebookService;

    @MessageMapping("/autosave")
    public void autosave(
            NoteAutosaveDto dto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        notebookService.autosave(
                user.getUserid(),
                dto.getNotebookId(),
                dto.getContent()
        );
    }
}

