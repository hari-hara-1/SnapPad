package com.webtech.snappad.dtos.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutosaveRequestDto {

    private Long notebookId;
    private String content;
}

