package com.example.epicgames.stubs;

import com.example.epicgames.DTO.account.req.AccountCreate;
import com.example.epicgames.DTO.account.req.AccountUpdate;
import com.example.epicgames.DTO.account.resp.AccountDTO;
import com.example.epicgames.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AccountStub {
    public static final Integer ID = 1;
    public static final Integer level = 0;
    public static final Integer size = 2;

    public static Account generateAccount() {
        return Account.builder()
                .id(ID)
                .user(UserStub.generateUser())
                .client(ClientStub.generateClient())
                .balance(BigDecimal.TEN)
                .isActive(true)
                .order(new ArrayList<>())
                .build();
    }

    public static List<Account> generateAccountsList() {
        return new ArrayList<>(
                Arrays.asList(
                        Account.builder()
                                .id(ID)
                                .user(UserStub.generateUser())
                                .client(ClientStub.generateClient())
                                .balance(BigDecimal.TEN)
                                .isActive(true)
                                .order(new ArrayList<>())
                                .build(),
                        Account.builder()
                                .id(ID + 1)
                                .user(UserStub.generateUser())
                                .client(ClientStub.generateClient())
                                .balance(BigDecimal.TEN)
                                .isActive(true)
                                .order(new ArrayList<>())
                                .build()
                )
        );
    }

    public static AccountUpdate generateAccountUpdate() {
        return AccountUpdate.builder()
                .id(ID)
                .balance(BigDecimal.TEN)
                .isActive(true)
                .build();
    }

    public static AccountCreate generateAccountCreate() {
        return AccountCreate.builder()
                .balance(BigDecimal.TEN)
                .isActive(true)
                .email("Email")
                .firstName("FirstName")
                .lastName("LastName")
                .login("Login")
                .password("Password")
                .phone("Phone")
                .build();
    }

    public static AccountDTO generateAccountDTO() {
        return AccountDTO.builder()
                .balance(BigDecimal.TEN)
                .isActive(true)
                .id(ID)
                .userId(UserStub.generateUser().getId())
                .clientId(ClientStub.generateClient().getId())
                .build();
    }
}
