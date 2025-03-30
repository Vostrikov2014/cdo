package org.example.authserver.service;

import org.example.authserver.model.AppUser;
import org.example.authserver.repository.AppUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
