package com.example.cdoback.controller;

import com.example.cdoback.model.AppUser;
import com.example.cdoback.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/users")
    public List<AppUser> getAppUsers() {
        return appUserService.getAppUsers();
    }

    @GetMapping("/user/{id}")
    public AppUser getAppUser(@PathVariable("id") Long id) {
        return appUserService.getAppUser(id);
    }

    @PostMapping("/user/{id}")
    public AppUser updateAppUser(@RequestBody() AppUser appUser, @PathVariable("id") Long id) {
        return appUserService.updateAppUser(appUser, id);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> newAppUser(@RequestBody() AppUser appUser) {
        AppUser newUser = appUserService.addAppUser(appUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    @GetMapping("/user")
    public String userEndpoint() {
        return "Доступ для USER";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Доступ для ADMIN";
    }
}
