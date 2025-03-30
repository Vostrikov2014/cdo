package org.example.authserver.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.example.authserver.model.OAuthClient;
import org.example.authserver.repository.OAuthClientRepository;
import org.example.authserver.repository.UserRepository;
import org.example.authserver.util.Jwks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class Config {

    @Autowired
    private OAuthClientRepository clientRepository;

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                    .redirectUri("http://localhost:3000") // Замените на ваш URL
                    .scope(OidcScopes.OPENID) // OIDC поддер/жка
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
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }*/
}
