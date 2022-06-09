package com.example.epicgames.stubs;

import com.example.epicgames.DTO.account.developer.req.DeveloperModify;
import com.example.epicgames.DTO.account.developer.resp.DeveloperDTO;
import com.example.epicgames.model.Developer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DeveloperStub {

    public static final Integer ID = 1;
    public static final Integer level = 0;
    public static final Integer size = 2;

    public static Developer generateDeveloper() {
        return Developer.builder()
                .id(ID)
                .info("Info")
                .firstName("FirstName")
                .middleName("MiddleName")
                .lastName("LastName")
                .games(new ArrayList<>())
                .build();
    }

    public static List<Developer> generateDevelopersList() {
        return new ArrayList<>(
                Arrays.asList(
                        Developer.builder()
                                .id(ID)
                                .info("Info")
                                .firstName("FirstName")
                                .middleName("MiddleName")
                                .lastName("LastName")
                                .games(new ArrayList<>())
                                .build(),
                        Developer.builder()
                                .id(ID + 1)
                                .info("Info")
                                .firstName("FirstName")
                                .middleName("MiddleName")
                                .lastName("LastName")
                                .games(new ArrayList<>())
                                .build()
                )
        );
    }

    public static DeveloperModify generateDeveloperModifyRequest() {
        return DeveloperModify.builder()
                .id(ID)
                .firstName("FirstName")
                .middleName("MiddleName")
                .lastName("LastName")
                .info("Info")
                .build();
    }

    public static DeveloperDTO generateDTO() {
        return DeveloperDTO.builder()
                .id(ID)
                .firstName("FirstName")
                .middleName("MiddleName")
                .lastName("LastName")
                .build();
    }

    public static DeveloperDTO generateDTO_2() {
        return DeveloperDTO.builder()
                .id(ID + 1)
                .firstName("FirstName")
                .middleName("MiddleName")
                .lastName("LastName")
                .build();
    }

}
