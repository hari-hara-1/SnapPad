package com.webtech.snappad.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.webtech.snappad.dtos.notebook.*;
import com.webtech.snappad.entities.Notebook;
import com.webtech.snappad.entities.User;
import com.webtech.snappad.repositories.NotebookRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotebookService {

    private final NotebookRepository notebookRepository;

    // ✅ CREATE NOTEBOOK
    public NotebookResponseDto createNotebook(
            NotebookCreateRequestDto dto,
            User user
    ) {
        Notebook notebook = new Notebook();
        notebook.setTitle(dto.getTitle());
        notebook.setContent(""); // empty by default
        notebook.setUser(user);
        notebook.setCreatedAt(LocalDateTime.now());
        notebook.setUpdatedAt(LocalDateTime.now());

        Notebook saved = notebookRepository.save(notebook);
        return toResponseDto(saved);
    }

    // ✅ GET ALL NOTEBOOKS FOR USER
    public List<NotebookSummaryDto> getUserNotebooks(Long userId) {
        return notebookRepository.findByUser_Userid(userId)
                .stream()
                .map(this::toSummaryDto)
                .collect(Collectors.toList());
    }

    // ✅ GET SINGLE NOTEBOOK
    public NotebookResponseDto getNotebook(Long notebookId, Long userId) {
        Notebook notebook = notebookRepository
                .findByNotebookIdAndUser_Userid(notebookId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notebook not found"));

        return toResponseDto(notebook);
    }

    // ✅ UPDATE NOTEBOOK
    public NotebookResponseDto updateNotebook(
            Long notebookId,
            NotebookUpdateRequestDto dto,
            Long userId
    ) {
        Notebook notebook = notebookRepository
                .findByNotebookIdAndUser_Userid(notebookId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notebook not found"));

        notebook.setTitle(dto.getTitle());
        notebook.setContent(dto.getContent());
        notebook.setUpdatedAt(LocalDateTime.now());

        Notebook updated = notebookRepository.save(notebook);
        return toResponseDto(updated);
    }

    // ✅ DELETE NOTEBOOK
    public void deleteNotebook(Long notebookId, Long userId) {
        Notebook notebook = notebookRepository
                .findByNotebookIdAndUser_Userid(notebookId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notebook not found"));

        notebookRepository.delete(notebook);
    }

    // ===============================
    // DTO MAPPERS (VERY IMPORTANT)
    // ===============================

    private NotebookResponseDto toResponseDto(Notebook notebook) {
        NotebookResponseDto dto = new NotebookResponseDto();
        dto.setNotebookId(notebook.getNotebookId());
        dto.setTitle(notebook.getTitle());
        dto.setContent(notebook.getContent());
        dto.setCreatedAt(notebook.getCreatedAt());
        dto.setUpdatedAt(notebook.getUpdatedAt());
        return dto;
    }

    private NotebookSummaryDto toSummaryDto(Notebook notebook) {
        NotebookSummaryDto dto = new NotebookSummaryDto();
        dto.setNotebookId(notebook.getNotebookId());
        dto.setTitle(notebook.getTitle());
        dto.setUpdatedAt(notebook.getUpdatedAt());
        return dto;
    }

    // ✅ AUTOSAVE FROM WEBSOCKET
    public void autosave(Long notebookId, Long userId, String content) {

        Notebook notebook = notebookRepository
                .findByNotebookIdAndUser_Userid(notebookId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Notebook not found"));

        notebook.setContent(content);
        notebook.setUpdatedAt(LocalDateTime.now());

        notebookRepository.save(notebook);
    }
}
