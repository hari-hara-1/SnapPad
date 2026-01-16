package com.webtech.snappad.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtech.snappad.entities.Notebook;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {

    Optional<Notebook> findByNotebookIdAndUser_Userid(
            Long notebookId,
            Long userid
    );

    List<Notebook> findByUser_Userid(Long userid);
}

