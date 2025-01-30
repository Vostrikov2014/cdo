package com.example.cdoback.controller;

import com.example.cdoback.entity.AppUserEntity;
import com.example.cdoback.dto.LoginRequest;
import com.example.cdoback.service.AppUserService;
import com.example.cdoback.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AppUserEntity> newAppUser(@RequestBody() AppUserEntity appUserEntity) {
        AppUserEntity newUser = appUserService.addAppUser(appUserEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // Basic authenticated
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try{
            boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (isAuthenticated) {
                session.setAttribute("user", loginRequest.getUsername());
                session.setAttribute("password", loginRequest.getPassword());
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
            return ResponseEntity.ok(appUserService.getAppUserByUsername(username.toString()).getFirstname()
                    + " " + appUserService.getAppUserByUsername(username.toString()).getLastname());
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
