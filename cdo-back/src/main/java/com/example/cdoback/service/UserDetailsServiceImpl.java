package com.example.cdoback.service;

import com.example.cdoback.database.entity.Role;
import com.example.cdoback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Попытка загрузки пользователя с именем: {}", username);

        return userRepository.findByUsername(username)
                .map(user -> {
                    log.info("Пользователь найден: {}", username);
                    return new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            Collections.singleton(Role.valueOf(user.getRole()))
                    );
                })
                .orElseThrow(() -> {
                    log.error("Не удалось найти пользователя: {}", username);
                    return new UsernameNotFoundException(
                            "Failed to retrieve user: " + username);
                });
    }
}
