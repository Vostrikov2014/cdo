package com.example.cdoback.dto;

import com.example.cdoback.database.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;
    private Role role;
}
