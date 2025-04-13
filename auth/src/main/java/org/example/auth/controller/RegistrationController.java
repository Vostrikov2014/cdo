package org.example.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auth.dto.RegisterRequest;
import org.example.auth.model.AppUser;
import org.example.auth.model.Authority;
import org.example.auth.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

/**
 * ИСОЛЬЗУЕТ ВСТРОЕННУЮ ФОРМУ РЕГИСТРАЦИИ<br>
 * Контроллер для обработки запросов регистрации новых пользователей.
 * Обеспечивает отображение формы регистрации и обработку данных нового пользователя.
 */
@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager userDetailsManager;

    /**
     * Отображает форму регистрации нового пользователя.
     *
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы регистрации
     */
    @GetMapping("/")
    public String registrationForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "registration";
    }

    /**
     * Обрабатывает отправку формы регистрации.
     * Создает нового пользователя с закодированным паролем и базовой ролью USER.
     *
     * @param user Объект с данными из формы регистрации
     * @param result Объект для хранения результатов валидации
     * @return Перенаправление на страницу входа при успешной регистрации
     *         или возврат к форме при ошибках валидации
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegisterRequest user, BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }

        UserDetails appUser = AppUser.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .enabled(true)
                .authorities(Set.of(Authority.builder()
                        .authority("ROLE_USER")
                        .build()))
                .build();

        userDetailsManager.createUser(appUser);

        return "redirect:/login";
    }
}
