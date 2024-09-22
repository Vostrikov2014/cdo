package com.example.cdoback.service;

import com.example.cdoback.database.entity.User;
import com.example.cdoback.dto.UserRegistrationDto;
import com.example.cdoback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(UserRegistrationDto registrationDto) {

        // Проверка, что пользователь с таким именем уже существует
        if (appUserRepository.findByUsername(registrationDto.getUsername()) != null) {
            return "Пользователь с таким именем уже существует";
        }

        // Проверка совпадения паролей
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return "Пароли не совпадают";
        }

        // Сохранение нового пользователя
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setRole("USER"); // Назначаем пользователю роль

        appUserRepository.save(newUser);
        return null; // Возвращаем null, если регистрация прошла успешно
    }
}
