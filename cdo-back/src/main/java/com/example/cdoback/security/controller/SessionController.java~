package com.example.cdoback.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/session-id")
    public String getSessionId(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/current-user")
    public String getCurrentUser() {
        // Получаем текущий SecurityContext
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // Получаем аутентифицированного пользователя
        Authentication authentication = securityContext.getAuthentication();

        // Если пользователь аутентифицирован, возвращаем его информацию
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Если пользователь является экземпляром UserDetails
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return "Current User: " + userDetails.getUsername() + ", Roles: " + userDetails.getAuthorities();
            } else {
                return "Current User: " + principal.toString();
            }
        }
        return "No user is currently authenticated.";
    }
}

