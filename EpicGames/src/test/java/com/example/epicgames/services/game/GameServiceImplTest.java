package com.example.epicgames.services.game;

import com.example.epicgames.model.Game;
import com.example.epicgames.repositories.GameRepository;
import com.example.epicgames.services.category.impls.CategoryServiceImpl;
import com.example.epicgames.services.developers.impls.DeveloperServiceImpl;
import com.example.epicgames.services.game.impls.GameServiceImpl;
import com.example.epicgames.stubs.GameStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    private GameServiceImpl service;

    @Mock
    private GameRepository repository;

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private DeveloperServiceImpl developerService;

    @BeforeEach
    void setup() {
        service = new GameServiceImpl(repository, developerService, categoryService);
    }

    @Test
    void successfulGetAll() {
        var expect = GameStub.generateGamesList();

        when(repository.findAll(PageRequest.of(GameStub.level, GameStub.size)))
                .thenReturn(new PageImpl<Game>(expect));

        var result = service.getAll(GameStub.level, GameStub.size);

        assertAll(() -> {
            assertEquals(2L, result.getTotalElements());

            var res1 = result.getContent().get(0);
            var res2 = result.getContent().get(1);

            assertEquals(res1.getId(), expect.get(0).getId());
            assertEquals(res1.getDevelopers().size(), expect.get(0).getDevelopers().size());
            assertEquals(res1.getCategories().size(), expect.get(0).getCategories().size());
            assertEquals(res1.getName(), expect.get(0).getName());
            assertEquals(res1.getPrice(), expect.get(0).getPrice());

            assertEquals(res2.getId(), expect.get(1).getId());
            assertEquals(res2.getDevelopers().size(), expect.get(1).getDevelopers().size());
            assertEquals(res2.getCategories().size(), expect.get(1).getCategories().size());
            assertEquals(res2.getName(), expect.get(1).getName());
            assertEquals(res2.getPrice(), expect.get(1).getPrice());
        });
    }

    @Test
    void successfulGet() {
        var expect = GameStub.generateGame();

        when(repository.getById(GameStub.ID)).thenReturn(expect);

        var result = service.get(GameStub.ID);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getDevelopers().size(), result.getDevelopers().size());
            assertEquals(expect.getCategories().size(), result.getCategories().size());
            assertEquals(expect.getName(), result.getName());
            assertEquals(expect.getPrice(), result.getPrice());
        });
    }

    @Test
    void successfulCreate() {
        var expect = GameStub.generateGame();

        when(repository.save(any())).thenReturn(expect);

        var result = service.create(GameStub.generateGameModifyRequest());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getDevelopers().size(), result.getDevelopers().size());
            assertEquals(expect.getCategories().size(), result.getCategories().size());
            assertEquals(expect.getName(), result.getName());
            assertEquals(expect.getPrice(), result.getPrice());
        });
    }

    @Test
    void successfulUpdate() {
        var expect = GameStub.generateGame();

        when(repository.save(any())).thenReturn(expect);

        var result = service.update(GameStub.generateGameModifyRequest());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getDevelopers().size(), result.getDevelopers().size());
            assertEquals(expect.getCategories().size(), result.getCategories().size());
            assertEquals(expect.getName(), result.getName());
            assertEquals(expect.getPrice(), result.getPrice());
        });
    }

    @Test
    void successfulDelete() {
        var deleteCaptor = ArgumentCaptor.forClass(Integer.class);

        service.delete(GameStub.ID);
        verify(repository, atLeast(1)).deleteById(deleteCaptor.capture());
        assertEquals(GameStub.ID, deleteCaptor.getValue());
    }

    @Test
    void successfulConvertToDTO() {
        var expect = GameStub.generateGameDTO();
        var getExpect = GameStub.generateGame();

        var result = service.convertToDTO(getExpect);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getDevelopers().size(), result.getDevelopers().size());
            assertEquals(expect.getCategories().size(), result.getCategories().size());
            assertEquals(expect.getName(), result.getName());
            assertEquals(expect.getPrice(), result.getPrice());
        });
    }

    @Test
    void successfulConvertToDTOString() {
    }

    @Test
    void successfulGetBookAuthorDTOs() {
    }

    @Test
    void successfulGetBookCategoryDTOs() {
    }

    @Test
    void successfulBindBookWithAuthors() {
    }

    @Test
    void successfulBindBookWithCategories() {
    }

}
