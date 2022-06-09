package com.example.epicgames.controllers.rest;

import com.example.epicgames.model.Account;
import com.example.epicgames.repositories.AccountRepository;
import com.example.epicgames.services.client.services.IClientService;
import com.example.epicgames.services.user.services.IUserService;
import com.example.epicgames.stubs.AccountStub;
import com.example.epicgames.stubs.ClientStub;
import com.example.epicgames.stubs.UserStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.epicgames.configuration.ServletRequestConfig.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository repository;

    @MockBean
    private IUserService userService;

    @MockBean
    private IClientService clientService;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAll() throws Exception {
        var request = AccountStub.generateAccountsList();

        when(repository.findAll(PageRequest.of(AccountStub.level, AccountStub.size)))
                .thenReturn(new PageImpl<Account>(request));

        mockMvc.perform(getRequest("/accounts?page=0&size=2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].balance", containsInAnyOrder(10, 10)))
                .andExpect(jsonPath("$[*].active", hasItems(true, true)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void get() throws Exception {
        var request = AccountStub.generateAccount();

        when(repository.getById(anyInt())).thenReturn(request);

        mockMvc.perform(getRequest("/accounts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getBalance().toString())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(String.valueOf(request.isActive()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        var request = AccountStub.generateAccountUpdate();
        request.setActive(false);

        var expect = AccountStub.generateAccount();
        expect.setActive(false);

        when(repository.getById(anyInt())).thenReturn(AccountStub.generateAccount());
        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(putRequest("/accounts/1", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getBalance().toString())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(String.valueOf(expect.isActive()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        var request = AccountStub.generateAccountCreate();

        var expect = AccountStub.generateAccount();

        when(userService.create(any())).thenReturn(UserStub.generateUserDTO());
        when(clientService.create(any())).thenReturn(ClientStub.generateClientDTO());
        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(postRequest("/accounts", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getBalance().toString())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(String.valueOf(expect.isActive()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        when(repository.getById(anyInt())).thenReturn(AccountStub.generateAccount());
        mockMvc.perform(deleteRequest("/accounts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(repository, atLeast(1)).deleteById(1);
        verify(clientService, atLeast(1)).delete(1);
        verify(userService, atLeast(1)).delete(1);
    }
}
