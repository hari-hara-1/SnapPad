package com.webtech.snappad.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webtech.snappad.dtos.notebook.NotebookCreateRequestDto;
import com.webtech.snappad.dtos.notebook.NotebookResponseDto;
import com.webtech.snappad.dtos.notebook.NotebookSummaryDto;
import com.webtech.snappad.dtos.notebook.NotebookUpdateRequestDto;
import com.webtech.snappad.entities.Notebook;
import com.webtech.snappad.entities.User;
import com.webtech.snappad.mappers.NotebookMapper;
import com.webtech.snappad.repositories.NotebookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public NotebookResponseDto createNotebook(
            NotebookCreateRequestDto dto,
            User user
    ) {
        Notebook notebook = NotebookMapper.fromCreateDto(dto, user);
        Notebook savedNotebook = notebookRepository.save(notebook);
        return NotebookMapper.toResponse(savedNotebook);
    }

    @Transactional(readOnly = true)
    public List<NotebookSummaryDto> getUserNotebooks(Long userId) {
        return notebookRepository.findByUser_Userid(userId)
                .stream()
                .map(NotebookMapper::toSummary)
                .toList();
    }

    @Transactional(readOnly = true)
    public NotebookResponseDto getNotebook(
            Long notebookId,
            Long userId
    ) {
        Notebook notebook = notebookRepository
                .findByNotebookIdAndUser_Userid(notebookId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Notebook not found"));

        return NotebookMapper.toResponse(notebook);
    }

    public NotebookResponseDto updateNotebook(
            Long notebookId,
            NotebookUpdateRequestDto dto,
            Long userId
    ) {
        Notebook notebook = notebookRepository
                .findByNotebookIdAndUser_Userid(notebookId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Notebook not found"));

        NotebookMapper.updateEntity(notebook, dto);
        Notebook updatedNotebook = notebookRepository.save(notebook);

        return NotebookMapper.toResponse(updatedNotebook);
    }

    public void deleteNotebook(
            Long notebookId,
            Long userId
    ) {
        Notebook notebook = notebookRepository
                .findByNotebookIdAndUser_Userid(notebookId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Notebook not found"));

        notebookRepository.delete(notebook);
    }
}


