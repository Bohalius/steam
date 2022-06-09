package com.example.epicgames.controllers.rest;

import com.example.epicgames.repositories.UserRepository;
import com.example.epicgames.services.account.services.IAccountService;
import com.example.epicgames.services.client.services.IClientService;
import com.example.epicgames.stubs.ClientStub;
import com.example.epicgames.stubs.DeveloperStub;
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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private IAccountService accountService;

    @MockBean
    private IClientService clientService;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAll() throws Exception {
        var request = UserStub.generateUsersList();

        when(repository.findAll(PageRequest.of(DeveloperStub.level, DeveloperStub.size)))
                .thenReturn(new PageImpl<>(request));

        mockMvc.perform(getRequest("/users?page=0&size=2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].username", containsInAnyOrder("Login 1", "Login 1")))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void get() throws Exception {
        var request = UserStub.generateUser();

        when(repository.getById(anyInt())).thenReturn(request);

        mockMvc.perform(getRequest("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getUsername())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getId().toString())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        var request = UserStub.generateUserModify();
        request.setUsername("New username");

        var expect = UserStub.generateUserDTO();

        var user = UserStub.generateUser();
        user.setUsername("New username");

        when(repository.save(any())).thenReturn(user);

        mockMvc.perform(putRequest("/users/1", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getUsername())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getId().toString())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        var request = UserStub.generateUserModify();

        var expect = UserStub.generateUserDTO();

        var user = UserStub.generateUser();

        when(repository.save(any())).thenReturn(user);

        mockMvc.perform(postRequest("/users", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getId().toString())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getUsername())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        when(accountService.findClientByUserId(anyInt())).thenReturn(ClientStub.generateClient());

        mockMvc.perform(deleteRequest("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(clientService, atLeast(1)).delete(1);
        verify(repository, atLeast(1)).deleteById(1);
    }
}
