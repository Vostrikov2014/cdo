package com.example.cdoback.security.config;

import com.example.cdoback.security.util.KeycloakRealmRoleConverter;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity        // для активации аннотаций @PreAuthorize и т.п.
@RequiredArgsConstructor
public class SecurityConfig {

    private final KeycloakRealmRoleConverter keycloakRealmRoleConverter;

    @Bean
    public KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
        KeycloakAuthenticationProvider provider = new KeycloakAuthenticationProvider();
        provider.setGrantedAuthoritiesMapper(new KeycloakGrantedAuthoritiesMapper());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(keycloakAuthenticationProvider())
                .build();
    }

    /*@Bean
    public KeycloakSecurityConfig keycloakSecurityConfig() {
        return new KeycloakSecurityConfig();
    }*/

    /*protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/**").authenticated() // Ограничиваем доступ для API
                .anyRequest().permitAll()  // Разрешаем публичные страницы
                .and()
                .csrf().disable();  // Отключаем CSRF, если не нужно

        // Дополнительная настройка фильтра для обработки запросов
        http.addFilterBefore(keycloakAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }*/

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)                     // Отключаем CSRF для прототипов REST API
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user").hasRole("USER")    // Доступ для роли USER
                        .requestMatchers("/api/admin").hasRole("ADMIN")  // Доступ для роли ADMIN
                        .anyRequest().permitAll() //.authenticated()                      // Требуется аутентификация для остальных запросов
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())) // Настройка JWT аутентификации
                );

        return http.build();
    }*/

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8091/realms/cdo-realm/protocol/openid-connect/certs")
                .build();
    }

    // Конвертер ролей для маппинга ролей из токена (если требуется)
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(keycloakRealmRoleConverter); // Кастомный конвертер ролей
        return converter;
    }


    // Настройка CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));     // Разрешить запросы с порта 3000
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Разрешить методы
        configuration.setAllowedHeaders(List.of("*"));                         // Разрешить любые заголовки
        configuration.setAllowCredentials(true);                                  // Разрешить отправку cookie

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);            // Применение ко всем маршрутам
        return source;
    }

    // Получение AuthenticationManager из AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Определение PasswordEncoder для кодирования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Для получения имени аутентифицированного пользователя в аудиторе
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (UserDetails) authentication.getPrincipal())
                .map(UserDetails::getUsername);
    }
}
