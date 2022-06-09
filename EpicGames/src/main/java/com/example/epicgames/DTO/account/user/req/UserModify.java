package com.example.epicgames.DTO.account.user.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModify {
    private Integer id;
    private String username;
    private String password;
}
