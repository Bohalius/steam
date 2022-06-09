package com.example.epicgames.services.user;

import com.example.epicgames.model.User;
import com.example.epicgames.repositories.UserRepository;
import com.example.epicgames.services.user.impls.UserServiceImpl;
import com.example.epicgames.stubs.UserStub;
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
class UserServiceImplTest {

    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setup() {
        service = new UserServiceImpl(repository);
    }

    @Test
    void successfulGetAll() {
        var expect = UserStub.generateUsersList();

        when(repository.findAll(PageRequest.of(UserStub.level, UserStub.size)))
                .thenReturn(new PageImpl<User>(expect));

        var result = service.getAll(0, 2);

        assertAll(() -> {
            assertEquals(2L, result.getTotalElements());

            var res1 = result.getContent().get(0);
            var res2 = result.getContent().get(1);

            assertEquals(res1.getId(), expect.get(0).getId());
            assertEquals(res1.getUsername(), expect.get(0).getUsername());

            assertEquals(res2.getId(), expect.get(1).getId());
            assertEquals(res2.getUsername(), expect.get(1).getUsername());
        });
    }

    @Test
    void successfulGet() {
        var expect = UserStub.generateUser();

        when(repository.getById(anyInt())).thenReturn(expect);

        var result = service.get(UserStub.ID);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getUsername(), result.getUsername());
        });
    }

    @Test
    void successfulCreate() {
        var expect = UserStub.generateUser();

        when(repository.save(any())).thenReturn(expect);

        var result = service.create(UserStub.generateUserModify());

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(result.getUsername(), expect.getUsername());
        });
    }

    @Test
    void successfulUpdate() {
        var expect = UserStub.generateUser();

        when(repository.save(any())).thenReturn(expect);

        var result = service.update(UserStub.generateUserModify());

        assertAll(() -> {
            assertEquals(result.getId(), expect.getId());
            assertEquals(result.getUsername(), expect.getUsername());
        });
    }

    @Test
    void successfulDelete() {
        var deleteCaptor = ArgumentCaptor.forClass(Integer.class);

        service.delete(UserStub.ID);
        verify(repository, atLeast(1)).deleteById(deleteCaptor.capture());
        assertEquals(UserStub.ID, deleteCaptor.getValue());
    }
}
