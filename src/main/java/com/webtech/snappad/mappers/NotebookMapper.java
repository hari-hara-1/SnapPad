package com.webtech.snappad.mappers;

import java.time.LocalDateTime;


import com.webtech.snappad.dtos.notebook.*;
import com.webtech.snappad.entities.Notebook;
import com.webtech.snappad.entities.User;

public class NotebookMapper {

    private NotebookMapper() {

    }

    public static NotebookResponseDto toResponse(Notebook notebook) {
        NotebookResponseDto dto = new NotebookResponseDto();
        dto.setNotebookId(notebook.getNotebookId());
        dto.setTitle(notebook.getTitle());
        dto.setContent(notebook.getContent());
        dto.setCreatedAt(notebook.getCreatedAt());
        dto.setUpdatedAt(notebook.getUpdatedAt());
        return dto;
    }

    public static NotebookSummaryDto toSummary(Notebook notebook) {
        NotebookSummaryDto dto = new NotebookSummaryDto();
        dto.setNotebookId(notebook.getNotebookId());
        dto.setTitle(notebook.getTitle());
        dto.setUpdatedAt(notebook.getUpdatedAt());
        return dto;
    }

    public static void updateEntity(
        Notebook notebook,
        NotebookUpdateRequestDto dto
    ) {
        if (dto.getTitle() != null) {
            notebook.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null) {
            notebook.setContent(dto.getContent());
        }
        notebook.setUpdatedAt(LocalDateTime.now());
    }

    public static Notebook fromCreateDto(
            NotebookCreateRequestDto dto,
            User user
    ) {
        Notebook notebook = new Notebook();
        notebook.setTitle(dto.getTitle());
        notebook.setContent("");
        notebook.setUser(user);
        notebook.setCreatedAt(LocalDateTime.now());
        notebook.setUpdatedAt(LocalDateTime.now());
        return notebook;
    }

}
