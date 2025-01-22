package com.example.cdoback.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "app_users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
}
