package org.example.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auth.dto.RegisterRequest;
import org.example.auth.model.AppUser;
import org.example.auth.model.Authority;
import org.example.auth.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager userDetailsManager;

    @GetMapping("/")
    public String registrationForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegisterRequest user, BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }

        UserDetails appUser = AppUser.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .enabled(true)
                .authorities(Set.of(Authority.builder()
                        .authority("ROLE_USER")
                        .build()))
                .build();

        userDetailsManager.createUser(appUser);

        return "redirect:/login";
    }
}
