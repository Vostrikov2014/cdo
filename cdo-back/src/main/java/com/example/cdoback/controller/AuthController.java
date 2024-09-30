package com.example.cdoback.controller;

import com.example.cdoback.dto.LoginRequestDto;
import com.example.cdoback.security.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        log.info("Login attempt for username: {}", loginRequestDto.getUsername());

        return appUserRepository.findByUsername(loginRequestDto.getUsername())
                .filter(user -> passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
                .map(user -> {
                    log.info("Login successful for username: {}", user.getUsername());
                    return ResponseEntity.ok("Login successful");
                })
                .orElseGet(() -> {
                    log.warn("Invalid login credentials for username: {}", loginRequestDto.getUsername());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
                });
    }

   /* @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser user) {
        if (appUserRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        appUserRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }*/
}
