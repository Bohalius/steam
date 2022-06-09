package com.example.epicgames.services.client;

import com.example.epicgames.model.Client;
import com.example.epicgames.repositories.ClientRepository;
import com.example.epicgames.services.client.impls.ClientServiceImpl;
import com.example.epicgames.stubs.ClientStub;
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
class ClientServiceImplTest {

    private ClientServiceImpl service;

    @Mock
    private ClientRepository repository;

    @BeforeEach
    void setup() {
        service = new ClientServiceImpl(repository);
    }

    @Test
    void successfulGetAll() {
        var expect = ClientStub.generateClientsList();

        when(repository.findAll(PageRequest.of(ClientStub.level, ClientStub.size)))
                .thenReturn(new PageImpl<Client>(expect));

        var result = service.getAll(0, 2);

        assertAll(() -> {
            assertEquals(2L, result.getTotalElements());

            var res1 = result.getContent().get(0);
            var res2 = result.getContent().get(1);

            assertEquals(res1.getId(), expect.get(0).getId());
            assertEquals(res1.getAddress(), expect.get(0).getAddress());
            assertEquals(res1.getEmail(), expect.get(0).getEmail());
            assertEquals(res1.getFirstName(), expect.get(0).getFirstName());
            assertEquals(res1.getLastName(), expect.get(0).getLastName());
            assertEquals(res1.getMiddleName(), expect.get(0).getMiddleName());
            assertEquals(res1.getPhone(), expect.get(0).getPhone());

            assertEquals(res2.getId(), expect.get(1).getId());
            assertEquals(res2.getAddress(), expect.get(1).getAddress());
            assertEquals(res2.getEmail(), expect.get(1).getEmail());
            assertEquals(res2.getFirstName(), expect.get(1).getFirstName());
            assertEquals(res2.getLastName(), expect.get(1).getLastName());
            assertEquals(res2.getMiddleName(), expect.get(1).getMiddleName());
            assertEquals(res2.getPhone(), expect.get(1).getPhone());
        });
    }

    @Test
    void successfulGet() {
        var expect = ClientStub.generateClient();

        when(repository.getById(anyInt())).thenReturn(expect);

        var result = service.get(ClientStub.ID);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getAddress(), result.getAddress());
            assertEquals(expect.getEmail(), result.getEmail());
            assertEquals(expect.getFirstName(), result.getFirstName());
            assertEquals(expect.getLastName(), result.getLastName());
            assertEquals(expect.getMiddleName(), result.getMiddleName());
            assertEquals(expect.getPhone(), result.getPhone());
        });
    }

    @Test
    void successfulCreate() {
        var expect = ClientStub.generateClient();

        when(repository.save(any())).thenReturn(expect);

        var result = service.create(ClientStub.generateClientModify());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getAddress(), result.getAddress());
            assertEquals(expect.getEmail(), result.getEmail());
            assertEquals(expect.getFirstName(), result.getFirstName());
            assertEquals(expect.getLastName(), result.getLastName());
            assertEquals(expect.getMiddleName(), result.getMiddleName());
            assertEquals(expect.getPhone(), result.getPhone());
        });
    }

    @Test
    void successfulUpdate() {
        var expect = ClientStub.generateClient();

        when(repository.save(any())).thenReturn(expect);

        var result = service.update(ClientStub.generateClientModify());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getAddress(), result.getAddress());
            assertEquals(expect.getEmail(), result.getEmail());
            assertEquals(expect.getFirstName(), result.getFirstName());
            assertEquals(expect.getLastName(), result.getLastName());
            assertEquals(expect.getMiddleName(), result.getMiddleName());
            assertEquals(expect.getPhone(), result.getPhone());
        });
    }

    @Test
    void successfulDelete() {
        var deleteCaptor = ArgumentCaptor.forClass(Integer.class);

        service.delete(ClientStub.ID);
        verify(repository, atLeast(1)).deleteById(deleteCaptor.capture());
        assertEquals(ClientStub.ID, deleteCaptor.getValue());
    }
}
