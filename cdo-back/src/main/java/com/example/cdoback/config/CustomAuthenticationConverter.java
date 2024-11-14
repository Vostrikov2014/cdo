package com.example.cdoback.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                        new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                        extractResourceRoles(source).stream()
                ).collect(Collectors.toSet())
        );
    }

    @SuppressWarnings("unchecked")
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {

        // Храним роли из разных частей токена
        Set<String> roles = new HashSet<>();

        // Получаем роли из realm_access
        Optional.ofNullable((Map<String, Object>) jwt.getClaim("realm_access"))
                .map(realmAccess -> (Collection<String>) realmAccess.get("roles"))
                .ifPresent(roles::addAll);


        // Получаем роли из resource_access.account
        Optional.ofNullable((Map<String, Object>) jwt.getClaim("resource_access"))
                .map(resourceAccess -> (Map<String, Object>) resourceAccess.get("account"))
                .map(account -> (Collection<String>) account.get("roles"))
                .ifPresent(roles::addAll);

        // Сюда напихиваем ресурсы из которых можно достать роли
        // ...

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        "ROLE_" + role.replace("-", "_").toUpperCase()))
                .collect(Collectors.toSet());
    }
}
