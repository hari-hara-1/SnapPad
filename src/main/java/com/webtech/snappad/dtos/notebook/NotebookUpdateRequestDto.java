package com.webtech.snappad.dtos.notebook;

import lombok.Data;

@Data
public class NotebookUpdateRequestDto {
    private String title;
    private String content;
}
