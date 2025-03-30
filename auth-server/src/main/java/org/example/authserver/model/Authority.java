package org.example.authserver.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

//@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser1 user;

    @Column(nullable = false)
    private String authority; // Например: "ROLE_ADMIN", "ROLE_USER"

    @Override
    public String getAuthority() {
        return authority;
    }
}