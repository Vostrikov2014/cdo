package com.example.cdoback.service;

import com.example.cdoback.model.AppUser;
import com.example.cdoback.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
