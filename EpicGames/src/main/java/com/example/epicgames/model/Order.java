package com.example.epicgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime orderDate;
    private BigDecimal totalSum;
    private String address;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(columnDefinition = "int[]")
    @Type(type = "com.example.epicgames.settings.CustomIntegerArrayType")
    private Integer[] games;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && Objects.equals(orderDate, order.orderDate) && Objects.equals(totalSum, order.totalSum) && Objects.equals(address, order.address) && account.equals(order.account) && Arrays.equals(games, order.games);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, orderDate, totalSum, address, account);
        result = 31 * result + Arrays.hashCode(games);
        return result;
    }
}
