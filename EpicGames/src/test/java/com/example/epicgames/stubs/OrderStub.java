package com.example.epicgames.stubs;

import com.example.epicgames.DTO.account.order.req.OrderCreate;
import com.example.epicgames.DTO.account.order.req.OrderUpdate;
import com.example.epicgames.DTO.account.order.resp.OrderDTO;
import com.example.epicgames.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class OrderStub {
    public static final Integer ID = 1;
    public static final Integer level = 0;
    public static final Integer size = 2;

    public static Order generateOrder() {
        return Order.builder()
                .id(ID)
                .account(AccountStub.generateAccount())
                .orderDate(LocalDateTime.now())
                .games(new Integer[]{1, 2})
                .address("Address")
                .totalSum(BigDecimal.TEN)
                .build();
    }

    public static OrderDTO generateOrderDTO() {
        return OrderDTO.builder()
                .id(ID)
                .address("Address")
                .orderDate(LocalDateTime.now())
                .games((Collections.singletonList(GameStub.generateGameDTO().toString())))
                .totalSum(BigDecimal.TEN)
                .account(AccountStub.generateAccountDTO().toString())
                .build();
    }

    public static List<Order> generateOrdersList() {
        return new ArrayList<>(
                Arrays.asList(
                        Order.builder()
                                .id(ID)
                                .account(AccountStub.generateAccount())
                                .orderDate(LocalDateTime.now())
                                .games(new Integer[]{1, 2})
                                .address("Address")
                                .totalSum(BigDecimal.TEN)
                                .build(),
                        Order.builder()
                                .id(ID + 1)
                                .account(AccountStub.generateAccount())
                                .orderDate(LocalDateTime.now())
                                .games(new Integer[]{1, 2})
                                .address("Address")
                                .totalSum(BigDecimal.TEN)
                                .build()
                )
        );
    }

    public static OrderCreate generateOrderCreateRequest() {
        return OrderCreate.builder()
                .id(ID)
                .accountId(AccountStub.ID)
                .address("Address")
                .gamesId(new ArrayList<>(Arrays.asList(1, 2)))
                .totalSum(BigDecimal.TEN)
                .build();
    }

    public static OrderUpdate generateOrderUpdateRequest() {
        return OrderUpdate.builder()
                .id(ID)
                .address("Address")
                .totalSum(BigDecimal.TEN)
                .build();
    }
}
