package com.example.cdoback.service;

import com.example.cdoback.dto.UserRegistrationDto;
import com.example.cdoback.model.AppUser;
import com.example.cdoback.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        AppUser newUser = new AppUser();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setRole("ROLE_USER"); // Назначаем пользователю роль

        appUserRepository.save(newUser);
        return null; // Возвращаем null, если регистрация прошла успешно
    }
}
