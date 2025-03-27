package com.example.cdoback.service;

import com.example.cdoback.entity.AppUserEntity;
import com.example.cdoback.repository.AppUserRepository;
import com.example.cdoback.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    //private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserEntity appUserEntity = appUserRepository.findByUsername(username);
        if (appUserEntity == null) {
            throw new UsernameNotFoundException("Пользователь " + username + " не найден.");
        }

        // Получаем роли пользователя
        /*List<GrantedAuthority> authorities = authorityRepository.findByUsername(username)
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());*/

        return new UserPrincipal(appUserEntity);
    }
}
