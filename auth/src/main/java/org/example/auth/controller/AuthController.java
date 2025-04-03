package org.example.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auth.dto.RegisterRequest;
import org.example.auth.model.AppUser;
import org.example.auth.model.Authority;
import org.example.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager userDetailsManager;

    @GetMapping
    public String registrationForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "registration";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/register/server")
    public String registerUser(@Valid RegisterRequest registerRequest, BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }

        UserDetails user = AppUser.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .authorities(Set.of(Authority.builder()
                        .authority("ROLE_USER")
                        .build()))
                .build();

        userDetailsManager.createUser(user);

        return "redirect:/login?registered";
    }
}
