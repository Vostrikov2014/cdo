package com.example.cdoback.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    private Long id;
    private String username;
    private String password;
}
