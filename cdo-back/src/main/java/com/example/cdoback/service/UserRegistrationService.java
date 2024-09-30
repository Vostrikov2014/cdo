package com.example.cdoback.service;

import com.example.cdoback.security.AppUser;
import com.example.cdoback.dto.UserRegistrationDto;
import com.example.cdoback.security.AppUserRepository;
import com.example.cdoback.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(UserRegistrationDto registrationDto) {

        // Проверка, что пользователь с таким именем уже существует
        if (appUserRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            return "Пользователь с таким именем уже существует";
        }

        // Проверка совпадения паролей
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return "Пароли не совпадают";
        }

        // Сохранение нового пользователя
        AppUser newAppUser = AppUser.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        appUserRepository.save(newAppUser);

        return null;
    }
}
