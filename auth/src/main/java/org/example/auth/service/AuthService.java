package org.example.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.auth.dto.RegisterRequest;
import org.example.auth.model.AppUser;
import org.example.auth.model.Authority;
import org.example.auth.repository.AppUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {
        if (userDetailsManager.userExists(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserDetails appUser = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .authorities(Set.of(Authority.builder()
                        .authority("ROLE_USER")
                        .build()))
                .build();

        userDetailsManager.createUser(appUser);
    }

    public boolean authenticate(String username, String password) {
        AppUser appUser = userRepository.findByUsername(username);
        if (!appUser.getUsername().equals(username)) {
            throw new UsernameNotFoundException("User name or Password is incorrect");
        }
        if (appUser.getPassword().equals(passwordEncoder.encode(password))) {
            throw new BadCredentialsException("User name or Password is incorrect");
        }

        return true;
    }
}
