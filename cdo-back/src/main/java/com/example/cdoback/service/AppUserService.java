package com.example.cdoback.service;

import com.example.cdoback.model.AppUser;
import com.example.cdoback.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    public AppUser getAppUser(Long id) {
        return appUserRepository.findById(id).orElse(null);
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
