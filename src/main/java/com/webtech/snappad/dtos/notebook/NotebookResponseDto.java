package com.webtech.snappad.dtos.notebook;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NotebookResponseDto {
    private Long notebookId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

