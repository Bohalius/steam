package com.example.epicgames.services.developer;

import com.example.epicgames.model.Developer;
import com.example.epicgames.repositories.DeveloperRepository;
import com.example.epicgames.services.developers.impls.DeveloperServiceImpl;
import com.example.epicgames.stubs.CategoryStub;
import com.example.epicgames.stubs.DeveloperStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class DeveloperServiceImplTest {
    private DeveloperServiceImpl service;

    @Mock
    private DeveloperRepository repository;

    @BeforeEach
    void setup() {
        service = new DeveloperServiceImpl(repository);
    }

    @Test
    void successfulGetAll() {
        var expect = DeveloperStub.generateDevelopersList();

        when(repository.findAll(PageRequest.of(CategoryStub.level, CategoryStub.size)))
                .thenReturn(new PageImpl<Developer>(expect));

        var result = service.getAll(0, 2);

        assertAll(() -> {
            assertEquals(2L, result.getTotalElements());

            var res1 = result.getContent().get(0);
            var res2 = result.getContent().get(1);

            assertEquals(res1.getId(), expect.get(0).getId());
            assertEquals(res1.getFirstName(), expect.get(0).getFirstName());
            assertEquals(res1.getLastName(), expect.get(0).getLastName());
            assertEquals(res1.getMiddleName(), expect.get(0).getMiddleName());

            assertEquals(res2.getId(), expect.get(1).getId());
            assertEquals(res2.getFirstName(), expect.get(1).getFirstName());
            assertEquals(res2.getLastName(), expect.get(1).getLastName());
            assertEquals(res2.getMiddleName(), expect.get(1).getMiddleName());
        });
    }

    @Test
    void successfulGet() {
        var expect = DeveloperStub.generateDeveloper();

        when(repository.getById(DeveloperStub.ID)).thenReturn(expect);

        var result = service.get(DeveloperStub.ID);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getFirstName(), result.getFirstName());
            assertEquals(expect.getLastName(), result.getLastName());
            assertEquals(expect.getMiddleName(), result.getMiddleName());
        });
    }

    @Test
    void successfulCreate() {
        var expect = DeveloperStub.generateDeveloper();
        when(repository.save(any())).thenReturn(expect);

        var result = service.create(DeveloperStub.generateDeveloperModifyRequest());

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(expect.getFirstName(), result.getFirstName());
            assertEquals(expect.getLastName(), result.getLastName());
            assertEquals(expect.getMiddleName(), result.getMiddleName());
        });
    }

    @Test
    void successfulUpdate() {
        var expect = DeveloperStub.generateDeveloper();

        when(repository.save(any())).thenReturn(expect);

        var result = service.update(DeveloperStub.generateDeveloperModifyRequest());

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(expect.getFirstName(), result.getFirstName());
            assertEquals(expect.getLastName(), result.getLastName());
            assertEquals(expect.getMiddleName(), result.getMiddleName());
        });
    }

    @Test
    void successfulDelete() {
        var deleteCaptor = ArgumentCaptor.forClass(Integer.class);

        service.delete(DeveloperStub.ID);
        verify(repository, atLeast(1)).deleteById(deleteCaptor.capture());
        assertEquals(DeveloperStub.ID, deleteCaptor.getValue());
    }

    private <T> CrudRepository<T, Integer> verify(DeveloperRepository repository, VerificationMode atLeast) {
        return null;
    }

    @Test
    void successfulConvertToDTO() {
        var expect = DeveloperStub.generateDTO();
        var getExpect = DeveloperStub.generateDeveloper();

        when(repository.getById(DeveloperStub.ID)).thenReturn(getExpect);

        var result = service.convertToDTO(DeveloperStub.ID);

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(expect.getFirstName(), result.getFirstName());
            assertEquals(expect.getLastName(), result.getLastName());
            assertEquals(expect.getMiddleName(), result.getMiddleName());
        });
    }
}
