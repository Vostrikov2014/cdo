package com.example.cdoback.controller;

import com.example.cdoback.dto.RegistrationDto;
import com.example.cdoback.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;

    // Отображение страницы регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    // Обработка формы регистрации
    @PostMapping("/register")
    public String registerUser(RegistrationDto registrationDto, Model model) {
        String errorMessage = userRegistrationService.register(registrationDto);
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            return "register"; // Возвращаемся на страницу регистрации с сообщением об ошибке
        }
        return "redirect:/auth/login"; // Редирект на страницу входа после успешной регистрации
    }
}
