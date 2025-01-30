package com.example.cdoback.controller;

import com.example.cdoback.model.AppUser;
import com.example.cdoback.service.AppUserService;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/user")
    public String userEndpoint() {
        return "Доступ для USER";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Доступ для ADMIN";
    }
}
