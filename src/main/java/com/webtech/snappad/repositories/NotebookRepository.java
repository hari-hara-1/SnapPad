package com.webtech.snappad.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtech.snappad.entities.Notebook;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    List<Notebook> findByUserid(String userid);

    Optional<Notebook> findByNotebookidAndUserid(String notebookId, String userId);
}
