package com.example.epicgames.DTO.account.order.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTO {
    private Integer id;
    private LocalDateTime orderDate;
    private BigDecimal totalSum;
    private String address;

    private String account;

    private List<String> games;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", totalSum=" + totalSum +
                ", address='" + address + '\'' +
                ", account='" + account + '\'' +
                ", games=" + games +
                '}';
    }
}
