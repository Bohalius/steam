package com.example.epicgames.repos;

import com.example.epicgames.model.Category;
import com.example.epicgames.model.Game;
import com.example.epicgames.repositories.CategoryRepository;
import com.example.epicgames.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class CategoryRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("Steam")
            .withUsername("postgres")
            .withPassword("admin")
            .withExposedPorts(5432);

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void getGameCategoriesNames() {
        insertCategories();
        insertGames();
        Optional<List<Category>> list = categoryRepository.getGameCategoriesNames(1);
        assertThat(list.isPresent()).isEqualTo(true);
        assertThat(list.get().size()).isEqualTo(2);
    }

    private void insertCategories() {
        categoryRepository.save(Category.builder()
                .id(1)
                .name("Name 1")
                .build());
        categoryRepository.save(Category.builder()
                .id(2)
                .name("Name 2")
                .build());
        categoryRepository.flush();
    }

    private void insertGames() {
        gameRepository.save(Game.builder()
                .developers(new ArrayList<>())
                .categories(new ArrayList<>(
                        Arrays.asList(
                                Category.builder()
                                        .id(1)
                                        .name("Name 1")
                                        .build(),
                                Category.builder()
                                        .id(2)
                                        .name("Name 2")
                                        .build()
                        )

                ))
                .publishingHouse("PH")
                .id(1)
                .price(BigDecimal.TEN)
                .name("Game")
                .build());
        gameRepository.flush();
    }
}
