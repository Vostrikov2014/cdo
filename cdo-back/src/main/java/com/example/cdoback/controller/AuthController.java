package com.example.cdoback.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String userLogin() {
        return null;
    }

    @PostMapping("/register")
    public String userRegister() {
        return null;
    }
}
