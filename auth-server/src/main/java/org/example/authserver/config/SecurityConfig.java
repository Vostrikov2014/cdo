package org.example.authserver.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.example.authserver.model.OAuthClient;
import org.example.authserver.repository.OAuthClientRepository;
import org.example.authserver.service.Jwks;
import org.example.authserver.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //@Autowired
    //private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private OAuthClientRepository clientRepository;

    @Bean
    public SecurityFilterChain asFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(withDefaults());

        // Настройка доступа
        //OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/oauth2/**", "/webjars/**", "/error").permitAll()
                .anyRequest().authenticated());

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/oauth2/**"))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

        // Используем новый способ регистрации OAuth2AuthorizationServerConfigurer
        http.with(new OAuth2AuthorizationServerConfigurer(), authorizationServerConfigurer -> {
            authorizationServerConfigurer.oidc(withDefaults()); // Включаем OIDC
        });

        // Обработка ошибок
        http.exceptionHandling(
                e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        );

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        List<OAuthClient> clientList = clientRepository.findAll();

        if (clientList.isEmpty()) {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("client-id")
                    .clientSecret("{noop}client-secret") // NoOpEncoder для тестов
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .redirectUri("http://localhost:8080/login/oauth2/code/client-id") // Замените на ваш URL
                    .scope(OidcScopes.OPENID) // OIDC поддержка
                    .scope("profile")
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                    .build();

            return new InMemoryRegisteredClientRepository(registeredClient);

        } else {

            List<RegisteredClient> registeredClientList = clientList.stream()
                    .map(oAuthClient -> RegisteredClient.withId(oAuthClient.getId().toString())
                            .clientId(oAuthClient.getClientId())
                            .clientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(oAuthClient.getClientSecret()))
                            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                            .redirectUri(oAuthClient.getRedirectUri())
                            .scope(oAuthClient.getScopes())
                            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                            .build()
                    ).collect(Collectors.toList());

            return new InMemoryRegisteredClientRepository(registeredClientList);
        }
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet((JWK) rsaKey);

        // Возвращаем список всех ключей доступных для подписи токенов
        // Тоже-самое можно сделать без лямбды
        //return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) {
                return jwkSelector.select(jwkSet);
            }
        };
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> context.getClaims().claim("custom_claim", "custom_value");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
}
