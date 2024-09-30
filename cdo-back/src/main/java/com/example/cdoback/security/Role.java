package com.example.cdoback.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name(); // Return the name of the role as the authority (e.g., "USER" or "ADMIN")
    }
}
