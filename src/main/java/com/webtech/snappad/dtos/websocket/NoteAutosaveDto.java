package com.webtech.snappad.dtos.websocket;

import lombok.Data;

@Data
public class NoteAutosaveDto {
    private Long notebookId;
    private String content;
}

