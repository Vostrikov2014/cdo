package com.example.cdoback.security.controller;

import com.example.cdoback.dto.RegistrationDto;
import com.example.cdoback.security.*;
import com.example.cdoback.security.dto.AuthRequest;
import com.example.cdoback.security.entity.UserEntity;
import com.example.cdoback.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

        //TODO тут нужно переделать заполнение аутентификации
        // вернуть проверку а потом идентификацию или как то еще...

        /*log.info("Login attempt for username: {}", authRequest.getUsername());

        UserDetails user = userDetailsServiceImpl.loadUserByUsername(authRequest.getUsername());

        // Проверка пароля
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            log.warn("Invalid login credentials for username: {}", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        }

        // Сохранение текущего залогиненного пользователя
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities()));

        *//*try {
            // Аутентификация пользователя
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(AuthRequest.getUsername(), AuthRequest.getPassword()));
            // Генерация токена
            String token = jwtTokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/password");
        }*//*

        log.info("Login successful for username: {}", user.getUsername());
        return ResponseEntity.ok("Login successful");*/

        log.info("Login attempt for username: {}", authRequest.getUsername());

        try {
            // Выполнение аутентификации
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Сохраняем аутентификацию в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Успешная аутентификация, можно вернуть информацию о пользователе
            UserDetails user = userDetailsServiceImpl.loadUserByUsername(authRequest.getUsername());

            // Возвращаем успешный ответ, например, имя пользователя
            return ResponseEntity.ok("Login successful for user: " + user.getUsername());

        } catch (Exception e) {
            log.warn("Invalid login credentials for username: {}", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            return new ResponseEntity<>("User is token!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = UserEntity.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
