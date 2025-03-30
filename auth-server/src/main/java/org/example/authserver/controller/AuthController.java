package org.example.authserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authserver.dto.LoginRequest;
import org.example.authserver.dto.RegisterRequest;
import org.example.authserver.model.AppUser1;
import org.example.authserver.service.AppUserService;
import org.example.authserver.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // Basic authenticated
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (isAuthenticated) {
                session.setAttribute("user", loginRequest.getUsername());
                //session.setAttribute("password", loginRequest.getPassword());
                System.out.println("Session ID: " + session.getId());
                System.out.println("Username: " + session.getAttribute("user"));
                return ResponseEntity.status(HttpStatus.CREATED).body("Login was successful!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User name or Password is incorrect");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsernameFromSession(HttpSession session) {
        Object username = session.getAttribute("user");
        if (username != null) {
            return ResponseEntity.ok(appUserService.getAppUserByUsername(username.toString())
                    + " " + appUserService.getAppUserByUsername(username.toString()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user logged in.");
    }

    @GetMapping("/username-str")
    public ResponseEntity<String> getUsernameByString(String localUsername) {
        if (localUsername != null) {
            return ResponseEntity.ok(appUserService.getAppUserByUsername(localUsername)
                    + " " + appUserService.getAppUserByUsername(localUsername));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user logged in.");
    }

    @GetMapping("/session")
    public ResponseEntity<?> checkSession(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity.ok(Map.of("username", auth.getName(), "authenticated", true));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated", false));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return ResponseEntity.ok(Map.of("message", "Вы вышли из системы"));
    }

    @PostMapping("/auth/login")
    public String userLogin() {
        return null;
    }

    @PostMapping("/auth/register")
    public String userRegister() {
        return null;
    }
}
