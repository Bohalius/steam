package com.example.epicgames.stubs;

import com.example.epicgames.DTO.account.game.req.GameModify;
import com.example.epicgames.DTO.account.game.resp.GameDTO;
import com.example.epicgames.model.Game;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GameStub {
    public static final Integer ID = 1;
    public static final Integer level = 0;
    public static final Integer size = 2;

    public static Game generateGame() {
        return Game.builder()
                .id(ID)
                .name("Name")
                .price(BigDecimal.TEN)
                .publishingHouse("PH")
                .categories(CategoryStub.generateCategoriesList())
                .developers(DeveloperStub.generateDevelopersList())
                .build();
    }

    public static GameDTO generateGameDTO() {
        return GameDTO.builder()
                .id(ID)
                .categories(new ArrayList<>(Arrays.asList(
                        CategoryStub.generateDTO(),
                        CategoryStub.generateDTO_2()
                )))
                .developers(new ArrayList<>(Arrays.asList(
                        DeveloperStub.generateDTO(),
                        DeveloperStub.generateDTO_2()
                )))
                .name("Name")
                .price(BigDecimal.TEN)
                .build();
    }

    public static List<Game> generateGamesList() {
        return new ArrayList<>(
                Arrays.asList(
                        Game.builder()
                                .id(ID)
                                .name("Name")
                                .price(BigDecimal.TEN)
                                .publishingHouse("PH")
                                .categories(new ArrayList<>())
                                .developers(new ArrayList<>())
                                .build(),
                        Game.builder()
                                .id(ID + 1)
                                .name("Name")
                                .price(BigDecimal.TEN)
                                .publishingHouse("PH")
                                .categories(new ArrayList<>())
                                .developers(new ArrayList<>())
                                .build()
                )
        );
    }

    public static GameModify generateGameModifyRequest() {
        return GameModify.builder()
                .id(ID)
                .developers(new ArrayList<>())
                .categories(new ArrayList<>())
                .name("Name")
                .price(BigDecimal.TEN)
                .publishingHouse("PH")
                .build();
    }
}
