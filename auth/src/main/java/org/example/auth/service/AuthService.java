package org.example.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.auth.dto.RegisterRequest;
import org.example.auth.model.AppUser;
import org.example.auth.repository.AppUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JdbcUserDetailsManager userDetailsManager;

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerNewUser(RegisterRequest request) {
        if (userDetailsManager.userExists(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserDetails appUser = AppUser.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                //.roles(roles)
                .build();

        userDetailsManager.createUser(appUser);
    }

    @Transactional
    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .roles(Set.of("USER"))
                .build();

        userRepository.save(user);
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
