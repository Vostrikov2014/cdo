package com.example.cdoback.security.util;

import com.example.cdoback.security.Role;
import com.example.cdoback.security.UserRepository;
import com.example.cdoback.security.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("user").orElse(null) == null) {
            UserEntity userEntity = UserEntity.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER)
                    .build();
            userRepository.save(userEntity);
        }

        if (userRepository.findByUsername("admin").orElse(null) == null) {
            UserEntity userEntity = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(userEntity);
        }
    }
}
