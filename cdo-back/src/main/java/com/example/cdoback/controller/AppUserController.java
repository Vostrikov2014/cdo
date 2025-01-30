package com.example.cdoback.controller;

import com.example.cdoback.entity.AppUserEntity;
import com.example.cdoback.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/users")
    public List<AppUserEntity> getAppUsers() {
        return appUserService.getAppUsers();
    }

    @GetMapping("/user/{id}")
    public AppUserEntity getAppUser(@PathVariable("id") Long id) {
        return appUserService.getAppUser(id);
    }

    @PostMapping("/user/{id}")
    public AppUserEntity updateAppUser(@RequestBody() AppUserEntity appUserEntity, @PathVariable("id") Long id) {
        return appUserService.updateAppUser(appUserEntity, id);
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
