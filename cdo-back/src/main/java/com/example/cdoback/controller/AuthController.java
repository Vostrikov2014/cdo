package com.example.cdoback.controller;

import com.example.cdoback.model.AppUser;
import com.example.cdoback.model.LoginRequest;
import com.example.cdoback.service.AppUserService;
import com.example.cdoback.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> newAppUser(@RequestBody() AppUser appUser) {
        AppUser newUser = appUserService.addAppUser(appUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // Basic authenticated
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try{
            boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (isAuthenticated) {
                session.setAttribute("user", loginRequest.getUsername());
                System.out.println("Session ID: " + session.getId());
                System.out.println("Username: " + session.getAttribute("user"));
                return ResponseEntity.status(HttpStatus.CREATED).body("Loin was successful!");
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

    @PostMapping("/auth/login")
    public String userLogin() {
        return null;
    }

    @PostMapping("/auth/register")
    public String userRegister() {
        return null;
    }
}
