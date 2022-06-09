package com.example.epicgames.DTO.account.order.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderCreate {
    private Integer id;
    private BigDecimal totalSum;
    private String address;

    private Integer accountId;

    private List<Integer> gamesId;
}
