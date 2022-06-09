package com.example.epicgames.DTO.account.client.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientModify {
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String phone;
    private String email;
}
