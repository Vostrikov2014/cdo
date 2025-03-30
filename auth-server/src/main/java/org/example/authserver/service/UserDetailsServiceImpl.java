package org.example.authserver.service;

import org.example.authserver.model.AppUser;
import org.example.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //return new SecurityUser(userRepository.findByUsername(username)
        //        .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Преобразуем Authority в GrantedAuthority
        List<GrantedAuthority> authorities = appUser.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList());

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(authorities)  // Уже преобразованные GrantedAuthority
                .build();

    }
}
