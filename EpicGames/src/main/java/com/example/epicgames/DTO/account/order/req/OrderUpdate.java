package com.example.epicgames.DTO.account.order.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderUpdate {
    private Integer id;
    private BigDecimal totalSum;
    private String address;
}
