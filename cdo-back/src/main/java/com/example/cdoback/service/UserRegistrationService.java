package com.example.cdoback.service;

import com.example.cdoback.security.entity.UserEntity;
import com.example.cdoback.dto.RegistrationDto;
import com.example.cdoback.security.UserRepository;
import com.example.cdoback.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RegistrationDto registrationDto) {

        // Проверка, что пользователь с таким именем уже существует
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            return "Пользователь с таким именем уже существует";
        }

        // Проверка совпадения паролей
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return "Пароли не совпадают";
        }

        // Сохранение нового пользователя
        UserEntity newUserEntity = UserEntity.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(newUserEntity);

        return null;
    }
}
