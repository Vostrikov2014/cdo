package com.example.cdoback.dto;

import com.example.cdoback.security.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;
    private Role role;
}
