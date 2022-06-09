package com.example.epicgames.DTO.account.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountCreate {
    private boolean isActive;
    private BigDecimal balance;

    private String login;
    private String password;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
