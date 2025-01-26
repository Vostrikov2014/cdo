package com.example.cdoback.service;

import com.example.cdoback.model.AppUser;
import com.example.cdoback.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public boolean authenticate(String username, String password) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (!appUser.getUsername().equals(username)) {
            throw new UsernameNotFoundException("User name or Password is incorrect");
        }
        /*if (appUser.getPasswordHash().equals(bCryptPasswordEncoder.encode(password))) {
           throw new BadCredentialsException("User name or Password is incorrect");
        }*/
        if (appUser.getPassword().equals(bCryptPasswordEncoder.encode(password))) {
            throw new BadCredentialsException("User name or Password is incorrect");
        }
        return true;
    }
}
