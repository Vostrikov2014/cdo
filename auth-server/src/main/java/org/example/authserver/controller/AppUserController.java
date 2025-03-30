package org.example.authserver.controller;

import org.example.authserver.model.AppUser;
import org.example.authserver.model.AppUser1;
import org.example.authserver.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/users")
    public List<AppUser> getAppUsers() {
        return appUserService.getAppUsers();
    }

    @GetMapping("/user/{id}")
    public AppUser getAppUser(@PathVariable("id") Long id) {
        return appUserService.getAppUser(id);
    }

    @PostMapping("/user/{id}")
    public AppUser updateAppUser(@RequestBody() AppUser appUserEntity, @PathVariable("id") Long id) {
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
