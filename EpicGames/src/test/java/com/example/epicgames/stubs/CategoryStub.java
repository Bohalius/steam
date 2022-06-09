package com.example.epicgames.stubs;

import com.example.epicgames.DTO.account.category.resp.CategoryDTO;
import com.example.epicgames.model.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CategoryStub {

    public static final Integer ID = 1;
    public static final Integer level = 0;
    public static final Integer size = 2;

    public static Category generateCategory() {
        return Category.builder()
                .id(ID)
                .name("Name 1")
                .games(new ArrayList<>())
                .build();
    }

    public static CategoryDTO generateDTO() {
        return CategoryDTO.builder()
                .id(ID)
                .name("Name 1")
                .build();
    }

    public static CategoryDTO generateDTO_2() {
        return CategoryDTO.builder()
                .id(ID + 1)
                .name("Name 2")
                .build();
    }

    public static List<Category> generateCategoriesList() {
        return new ArrayList<>(
                Arrays.asList(
                        Category.builder()
                                .id(ID)
                                .name("Name 1")
                                .games(new ArrayList<>())
                                .build(),
                        Category.builder()
                                .id(ID + 1)
                                .name("Name 2")
                                .games(new ArrayList<>())
                                .build()
                )
        );
    }
}
