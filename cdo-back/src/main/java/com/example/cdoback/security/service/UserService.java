package com.example.cdoback.security.service;

import com.example.cdoback.security.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    public AppUser loadUser(JwtAuthenticationToken token) {
        Jwt jwt = token.getToken();
        String username = jwt.getClaimAsString("preferred_username");
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        return new AppUser(username, authorities);
    }
}

