package com.webtech.snappad.entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String password;
}
