package com.webtech.snappad.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.webtech.snappad.dtos.notebook.*;
import com.webtech.snappad.entities.User;
import com.webtech.snappad.services.NotebookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notebooks")
@RequiredArgsConstructor
public class NotebookController {

    private final NotebookService notebookService;

    @PostMapping
    public ResponseEntity<NotebookResponseDto> createNotebook(
            @RequestBody NotebookCreateRequestDto dto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        NotebookResponseDto response =
                notebookService.createNotebook(dto, user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<NotebookSummaryDto>> getUserNotebooks(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                notebookService.getUserNotebooks(user.getUserid())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotebookResponseDto> getNotebook(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                notebookService.getNotebook(id, user.getUserid())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotebookResponseDto> updateNotebook(
            @PathVariable Long id,
            @RequestBody NotebookUpdateRequestDto dto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                notebookService.updateNotebook(id, dto, user.getUserid())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotebook(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        notebookService.deleteNotebook(id, user.getUserid());
        return ResponseEntity.noContent().build();
    }

}

