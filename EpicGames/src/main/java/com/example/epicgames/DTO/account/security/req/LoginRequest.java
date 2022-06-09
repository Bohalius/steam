package com.example.epicgames.DTO.account.security.req;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
