package com.webtech.snappad.dtos.notebook;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NotebookSummaryDto {
    private Long notebookId;
    private String title;
    private LocalDateTime updatedAt;
}

