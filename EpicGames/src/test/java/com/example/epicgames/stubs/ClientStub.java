package com.example.epicgames.stubs;

import com.example.epicgames.DTO.account.client.req.ClientModify;
import com.example.epicgames.DTO.account.client.resp.ClientDTO;
import com.example.epicgames.model.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ClientStub {

    public static final Integer ID = 1;
    public static final Integer level = 0;
    public static final Integer size = 2;

    public static Client generateClient() {
        return Client.builder()
                .id(ID)
                .firstName("First name")
                .middleName("Middle name")
                .lastName("Last name")
                .address("Address")
                .phone("Phone")
                .email("Email")
                .build();
    }

    public static List<Client> generateClientsList() {
        return new ArrayList<>(
                Arrays.asList(
                        Client.builder()
                                .id(ID)
                                .firstName("First name 1")
                                .middleName("Middle name 1")
                                .lastName("Last name 1")
                                .address("Address 1")
                                .phone("Phone 1")
                                .email("Email 1")
                                .build(),
                        Client.builder()
                                .id(ID + 1)
                                .firstName("First name 2")
                                .middleName("Middle name 2")
                                .lastName("Last name 2")
                                .address("Address 2")
                                .phone("Phone 2")
                                .email("Email 2")
                                .build()
                )
        );
    }

    public static ClientDTO generateClientDTO() {
        return ClientDTO.builder()
                .id(ID)
                .firstName("First name")
                .middleName("Middle name")
                .lastName("Last name")
                .address("Address")
                .phone("Phone")
                .email("Email")
                .build();
    }

    public static ClientModify generateClientModify() {
        return ClientModify.builder()
                .id(ID)
                .firstName("First name")
                .middleName("Middle name")
                .lastName("Last name")
                .address("Address")
                .phone("Phone")
                .email("Email")
                .build();
    }
}
