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
public class AccountUpdate {
    private Integer id;
    private boolean isActive;
    private BigDecimal balance;
}
