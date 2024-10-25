package com.example.cdoback.security.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class KeycloakLogoutHandler implements LogoutHandler {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            String token = (String) authentication.getCredentials();
            String logoutUrl = issuerUri + "/protocol/openid-connect/logout";

            restTemplate.postForEntity(logoutUrl, null, Void.class);
        }
    }
}

