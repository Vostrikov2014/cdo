package com.example.cdoback.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Keycloak
        /*httpSecurity
                .csrf(AbstractHttpConfigurer::disable)                              // Отключаем CSRF для прототипов REST API
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Включаем CORS
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").hasRole("DEFAULT_ROLES_CDO_REALM")  // Доступ для роли USER
                        .requestMatchers("/admin/**").hasRole("ADMIN")              // Доступ для роли ADMIN
                        .anyRequest().authenticated()                               // Требуется аутентификация для остальных запросов
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(token -> token.jwtAuthenticationConverter(new CustomAuthenticationConverter())));*/ // Настройка JWT аутентификации

        //Spring Security
        http
                .csrf(AbstractHttpConfigurer::disable)                                            // Отключаем CSRF для прототипов REST API
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))                // Включаем CORS
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/register", "/username").permitAll()    // Доступ без аутентификации
                        .anyRequest().authenticated()                                             // Требуется аутентификация для остальных запросов
                )
                .httpBasic(Customizer.withDefaults());                                            // Это использует стандартные настройки CORS


        return http.build();
    }

    // Определение BCryptPasswordEncoder для кодирования паролей
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Для информации: Если приложение не предоставляет компонент JwtDecoder,
    // то Spring Boot предоставит компонент по умолчанию, описанный ниже
    /*@Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }*/

    // Настройка CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));      // Разрешить запросы с порта 3000
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));  // Разрешить методы
        configuration.setAllowedHeaders(List.of("*"));                          // Разрешить любые заголовки
        configuration.setAllowCredentials(true);                                   // Разрешить отправку cookie

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);             // Применение ко всем маршрутам
        return source;
    }

    // Получение AuthenticationManager из AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Для получения имени аутентифицированного пользователя в аудиторе
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (UserDetails) authentication.getPrincipal())
                .map(UserDetails::getUsername);
    }
}
