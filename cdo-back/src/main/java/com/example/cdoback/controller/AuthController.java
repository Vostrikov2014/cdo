package com.example.cdoback.controller;

import com.example.cdoback.dto.LoginDto;
import com.example.cdoback.dto.RegistrationDto;
import com.example.cdoback.security.CastomUserDetailsService;
import com.example.cdoback.security.Role;
import com.example.cdoback.security.UserEntity;
import com.example.cdoback.security.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CastomUserDetailsService castomUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        log.info("Login attempt for username: {}", loginDto.getUsername());

        UserDetails user = castomUserDetailsService.loadUserByUsername(loginDto.getUsername());

        // Проверка пароля
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            log.warn("Invalid login credentials for username: {}", loginDto.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        }

        // Сохранение текущего залогиненного пользователя
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities()));

        log.info("Login successful for username: {}", user.getUsername());
        return ResponseEntity.ok("Login successful");

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
