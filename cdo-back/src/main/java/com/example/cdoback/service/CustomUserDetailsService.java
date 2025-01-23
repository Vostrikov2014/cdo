package com.example.cdoback.service;

import com.example.cdoback.model.AppUser;
import com.example.cdoback.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUserName(userName);

        if (appUser == null) {
            throw new UsernameNotFoundException("Пользователь " + userName + " не найден.");
        }

        return new UserPrincipal(appUser);
    }
}
