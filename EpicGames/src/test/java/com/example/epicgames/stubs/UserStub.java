package com.example.epicgames.stubs;

import com.example.epicgames.DTO.account.user.req.UserModify;
import com.example.epicgames.DTO.account.user.resp.UserDTO;
import com.example.epicgames.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class UserStub {

    public static final Integer ID = 1;
    public static final Integer level = 0;
    public static final Integer size = 2;

    public static User generateUser() {
        return User.builder()
                .id(ID)
                .password("Password")
                .username("Login")
                .build();
    }

    public static List<User> generateUsersList() {
        return new ArrayList<>(
                Arrays.asList(
                        User.builder()
                                .id(ID)
                                .password("Password 1")
                                .username("Login 1")
                                .build(),
                        User.builder()
                                .id(ID + 1)
                                .password("Password 1")
                                .username("Login 1")
                                .build()
                )
        );
    }

    public static UserModify generateUserModify() {
        return UserModify.builder()
                .id(ID)
                .password("Password")
                .username("Login")
                .build();
    }

    public static UserDTO generateUserDTO() {
        return UserDTO.builder()
                .id(ID)
                .username("Login")
                .build();
    }
}
