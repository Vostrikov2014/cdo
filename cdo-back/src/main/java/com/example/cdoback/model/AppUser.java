package com.example.cdoback.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "app_users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String passwordhash;
}
