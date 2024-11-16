package com.example.cdoback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public String userEndpoint() {
        return "Доступ для USER";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Доступ для ADMIN";
    }
}
