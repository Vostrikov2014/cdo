package com.example.cdoback.entity;

import jakarta.persistence.*;
import lombok.Data;

//@Entity
//@Table(name = "authorities")
@Data
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private AppUserEntity user;
}
