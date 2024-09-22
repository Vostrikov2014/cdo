package com.example.cdoback.controller;

import com.example.cdoback.database.entity.User;
import com.example.cdoback.dto.LoginRequestDto;
import com.example.cdoback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElse(null);
        if (user != null && passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {

            // Здесь можно добавить логику для генерации токена или сессии
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        }
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
