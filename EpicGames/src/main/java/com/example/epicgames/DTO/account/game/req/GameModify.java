package com.example.epicgames.DTO.account.game.req;

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
public class GameModify {
    List<Integer> developers;
    List<Integer> categories;
    private Integer id;
    private BigDecimal price;
    private String name;
    private String publishingHouse;

}
