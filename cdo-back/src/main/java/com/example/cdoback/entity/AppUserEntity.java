package com.example.cdoback.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
//@Entity
//@Table(name = "users")
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstname;
    private String lastname;
    private String email;
    private String passwordhash;

    @Column(nullable = false,
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities;
}
