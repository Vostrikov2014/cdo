package com.example.cdoback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (appUserRepository.findByUsername("user").orElse(null) == null) {
            AppUser appUser = AppUser.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ROLE_USER)
                    .build();
            appUserRepository.save(appUser);
        }

        if (appUserRepository.findByUsername("admin").orElse(null) == null) {
            AppUser appUser = AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            appUserRepository.save(appUser);
        }
    }
}
