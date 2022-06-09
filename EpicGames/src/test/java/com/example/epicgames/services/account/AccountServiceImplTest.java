package com.example.epicgames.services.account;

import com.example.epicgames.model.Account;
import com.example.epicgames.repositories.AccountRepository;
import com.example.epicgames.services.account.impls.AccountServiceImpl;
import com.example.epicgames.services.client.impls.ClientServiceImpl;
import com.example.epicgames.services.user.impls.UserServiceImpl;
import com.example.epicgames.stubs.AccountStub;
import com.example.epicgames.stubs.ClientStub;
import com.example.epicgames.stubs.UserStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    AccountRepository repository;
    private AccountServiceImpl service;
    @Mock
    private ClientServiceImpl clientService;
    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        service = new AccountServiceImpl(repository, userService, clientService);
    }

    @Test
    void successfulGetAll() {
        var expect = AccountStub.generateAccountsList();

        when(repository.findAll(PageRequest.of(AccountStub.page, AccountStub.size)))
                .thenReturn(new PageImpl<Account>(expect));

        var result = service.getAll(AccountStub.page, AccountStub.size);

        assertAll(() -> {
            assertEquals(2L, result.getTotalElements());

            var res1 = result.getContent().get(0);
            var res2 = result.getContent().get(1);

            assertEquals(res1.getId(), expect.get(0).getId());
            assertEquals(res1.getClientId(), expect.get(0).getClient().getId());
            assertEquals(res1.getUserId(), expect.get(0).getUser().getId());
            assertEquals(res1.getBalance(), expect.get(0).getBalance());
            assertEquals(res1.isActive(), expect.get(0).isActive());

            assertEquals(res2.getId(), expect.get(1).getId());
            assertEquals(res2.getClientId(), expect.get(1).getClient().getId());
            assertEquals(res2.getUserId(), expect.get(1).getUser().getId());
            assertEquals(res2.getBalance(), expect.get(1).getBalance());
            assertEquals(res2.isActive(), expect.get(1).isActive());

        });
    }

    @Test
    void successfulGet() {
        var expect = AccountStub.generateAccount();

        when(repository.getById(AccountStub.ID)).thenReturn(expect);

        var result = service.get(AccountStub.ID);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getClient().getId(), result.getClientId());
            assertEquals(expect.getUser().getId(), result.getUserId());
            assertEquals(expect.getBalance(), result.getBalance());
            assertEquals(expect.isActive(), result.isActive());
        });
    }

    @Test
    void successfulCreate() {
        var expect = AccountStub.generateAccount();

        when(repository.save(any())).thenReturn(expect);
        when(clientService.create(any())).thenReturn(ClientStub.generateClientDTO());
        when(userService.create(any())).thenReturn(UserStub.generateUserDTO());

        var result = service.create(AccountStub.generateAccountCreate());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getClient().getId(), result.getClientId());
            assertEquals(expect.getUser().getId(), result.getUserId());
            assertEquals(expect.getBalance(), result.getBalance());
            assertEquals(expect.isActive(), result.isActive());
        });
    }

    @Test
    void successfulUpdate() {
        var expect = AccountStub.generateAccount();

        when(repository.save(any())).thenReturn(expect);
        when(repository.getById(AccountStub.ID)).thenReturn(AccountStub.generateAccount());

        var result = service.update(AccountStub.generateAccountUpdate());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getClient().getId(), result.getClientId());
            assertEquals(expect.getUser().getId(), result.getUserId());
            assertEquals(expect.getBalance(), result.getBalance());
            assertEquals(expect.isActive(), result.isActive());
        });
    }

    @Test
    void successfulDelete() {
        var deleteCaptor = ArgumentCaptor.forClass(Integer.class);

        when(repository.getById(AccountStub.ID)).thenReturn(AccountStub.generateAccount());

        service.delete(AccountStub.ID);
        verify(repository, atLeast(1)).deleteById(deleteCaptor.capture());
        assertEquals(AccountStub.ID, deleteCaptor.getValue());
    }

    @Test
    void successfulFindClientByUserId() {
        var expect = AccountStub.generateAccount();

        when(repository.findByUserId(UserStub.ID)).thenReturn(expect);

        var result = service.findClientByUserId(UserStub.ID);

        assertAll(() -> {
            assertEquals(expect.getClient().getId(), result.getId());
            assertEquals(expect.getClient().getAddress(), result.getAddress());
            assertEquals(expect.getClient().getFirstName(), result.getFirstName());
            assertEquals(expect.getClient().getMiddleName(), result.getMiddleName());
            assertEquals(expect.getClient().getLastName(), result.getLastName());
            assertEquals(expect.getClient().getEmail(), result.getEmail());
            assertEquals(expect.getClient().getPhone(), result.getPhone());
        });
    }

    @Test
    void successfulFindUserByClientId() {
        var expect = AccountStub.generateAccount();

        when(repository.findByClientId(ClientStub.ID)).thenReturn(expect);

        var result = service.findUserByClientId(ClientStub.ID);

        assertAll(() -> {
            assertEquals(expect.getClient().getId(), result.getId());
            assertEquals(expect.getUser().getUsername(), result.getUsername());
            assertEquals(expect.getUser().getPassword(), result.getPassword());
        });
    }

    @Test
    void failedFindClientByUserId() {
        when(repository.findByUserId(UserStub.ID)).thenReturn(null);

        var result = service.findClientByUserId(UserStub.ID);

        assertNull(result);
    }

    @Test
    void failedFindUserByClientId() {

        when(repository.findByClientId(ClientStub.ID)).thenReturn(null);

        var result = service.findUserByClientId(ClientStub.ID);

        assertNull(result);
    }

    @Test
    void successfulConvertToDTOString() {
        var expect = AccountStub.generateAccount();
        var accountDTO = AccountStub.generateAccountDTO();

        when(repository.getById(AccountStub.ID)).thenReturn(expect);

        var result = service.convertToDTOString(AccountStub.ID);

        assertEquals(result, accountDTO.toString());
    }

    @Test
    void successfulConvertToDTO() {
        var expect = AccountStub.generateAccount();
        var getExpect = AccountStub.generateAccount();

        var result = service.convertToDTO(getExpect);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getClient().getId(), result.getClientId());
            assertEquals(expect.getUser().getId(), result.getUserId());
            assertEquals(expect.getBalance(), result.getBalance());
            assertEquals(expect.isActive(), result.isActive());
        });
    }
}
