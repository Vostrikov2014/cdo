package org.example.authserver.service;

import org.example.authserver.model.AppUser;
import org.example.authserver.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    public AppUser getAppUser(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    public AppUser getAppUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AppUser addAppUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    public AppUser updateAppUser(AppUser appUser, Long id) {
        appUser.setId(id);
        return appUserRepository.save(appUser);
    }

    public void deleteAppUser(Long id) {
        appUserRepository.deleteById(id);
    }
}
