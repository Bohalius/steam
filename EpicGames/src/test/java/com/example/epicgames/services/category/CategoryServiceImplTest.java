package com.example.epicgames.services.category;

import com.example.epicgames.model.Category;
import com.example.epicgames.repositories.CategoryRepository;
import com.example.epicgames.services.category.impls.CategoryServiceImpl;
import com.example.epicgames.stubs.CategoryStub;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private CategoryServiceImpl service;

    @Mock
    private CategoryRepository repository;

    @BeforeEach
    void setup() {
        service = new CategoryServiceImpl(repository);
    }

    @Test
    void successfulGetAll() {
        var expect = CategoryStub.generateCategoriesList();

        when(repository.findAll(PageRequest.of(CategoryStub.level, CategoryStub.size)))
                .thenReturn(new PageImpl<Category>(expect));

        var result = service.getAll(0, 2);

        assertAll(() -> {
            assertEquals(2L, result.getTotalElements());

            var res1 = result.getContent().get(0);
            var res2 = result.getContent().get(1);

            assertEquals(res1.getId(), expect.get(0).getId());
            assertEquals(res1.getName(), expect.get(0).getName());

            assertEquals(res2.getId(), expect.get(1).getId());
            assertEquals(res2.getName(), expect.get(1).getName());
        });
    }

    @Test
    void successfulGet() {
        var expect = CategoryStub.generateCategory();

        when(repository.getById(anyInt())).thenReturn(expect);

        var result = service.get(CategoryStub.ID);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getName(), result.getName());
        });
    }

    @Test
    void successfulCreate() {
        var expect = CategoryStub.generateCategory();

        when(repository.save(any())).thenReturn(expect);

        var result = service.create(CategoryStub.generateDTO());

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(result.getName(), expect.getName());
        });
    }

    @Test
    void successfulUpdate() {
        var expect = CategoryStub.generateCategory();

        when(repository.save(any())).thenReturn(expect);

        var result = service.update(CategoryStub.generateDTO());

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(result.getName(), expect.getName());
        });
    }

    @Test
    void successfulDelete() {
        var deleteCaptor = ArgumentCaptor.forClass(Integer.class);

        service.delete(CategoryStub.ID);
        verify(repository, atLeast(1)).deleteById(deleteCaptor.capture());
        assertEquals(CategoryStub.ID, deleteCaptor.getValue());
    }

    @Test
    void successfulConvertToDTO() {
        var expect = CategoryStub.generateDTO();
        var getExpect = CategoryStub.generateCategory();

        when(repository.getById(anyInt())).thenReturn(getExpect);

        var result = service.convertToDTO(CategoryStub.ID);

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(result.getName(), expect.getName());
        });
    }
}
