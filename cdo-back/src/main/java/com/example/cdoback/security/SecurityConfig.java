package com.example.cdoback.security;

import com.example.cdoback.security.service.UserDetailsServiceImpl;
import com.example.cdoback.security.util.CustomOAuth2LoginSuccessHandler;
import com.example.cdoback.security.util.JwtTokenFilter;
import com.example.cdoback.security.util.KeycloakLogoutHandler;
import com.example.cdoback.security.util.KeycloakRoleConverter;
import com.fasterxml.jackson.databind.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableMethodSecurity        //Spring 107 Method Security - использовать для проверки аутентификации
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(customOAuth2LoginSuccessHandler)) // OAuth2 login with Keycloak
                .and()
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutSuccessUrl("/")) // Define logout URL
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new KeycloakRoleConverter())));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "http://localhost:8091/realms/cdo-realm";
        return NimbusJwtDecoder.withJwkSetUri(issuerUri + "/protocol/openid-connect/certs").build();
    }

    @Bean
    JwtAuthenticationConverter authenticationConverter(
            Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
        var authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            return authoritiesConverter.convert(jwt.getClaims());
        });
        return authenticationConverter;
    }

    // FOR KeyCloak
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http
                .authorizeHttpRequests(auth -> auth.requestMatchers("error").permitAll()
                        .requestMatchers("/user/**").hasRole(Role.USER.getAuthority())
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        var converter = new JwtAuthenticationConverter();
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            var roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");

            return Stream.concat(authorities.stream(),
                            roles.stream()
                                    .filter(role -> role.startsWith("ROLE_"))
                                    .map(SimpleGrantedAuthority::new)
                                    .map(GrantedAuthority.class::cast))
                    .toList();

        });

        return converter;
    }*/

    // Основной метод конфигурации безопасности
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(csrf -> csrf
                //        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))        // Enable CSRF with cookies
                //        .ignoringRequestMatchers("/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.getAuthority())
                        .requestMatchers("/user/**").hasRole(Role.USER.getAuthority())            // Protect /user endpoints for USER role
                        .requestMatchers("/", "auth/register", "auth/login", "/css/**",
                                "conference/create", "conference/update", "conference/",
                                "conference/list", "/session-id", "/current-user").permitAll()   // Allow public access to these URLs
                        .anyRequest().authenticated())
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(new HttpSessionSecurityContextRepository()))      // Ограничение на одну сессию
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))                   // Создание сессии при необходимости
                .userDetailsService(userDetailsServiceImpl)                                          // Использование кастомного UserDetailsService
                //.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)       // Добавляем JWT фильтр
                .build();
    }*/

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
