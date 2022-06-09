package com.example.epicgames.controllers.rest;

import com.example.epicgames.model.Client;
import com.example.epicgames.repositories.ClientRepository;
import com.example.epicgames.stubs.ClientStub;
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
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository repository;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAll() throws Exception {
        var request = ClientStub.generateClientsList();

        when(repository.findAll(PageRequest.of(ClientStub.level, ClientStub.size)))
                .thenReturn(new PageImpl<Client>(request));

        mockMvc.perform(getRequest("/clients?page=0&size=2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].firstName", containsInAnyOrder("First name 1", "First name 2")))
                .andExpect(jsonPath("$[*].middleName", containsInAnyOrder("Middle name 1", "Middle name 2")))
                .andExpect(jsonPath("$[*].lastName", containsInAnyOrder("Last name 1", "Last name 2")))
                .andExpect(jsonPath("$[*].address", containsInAnyOrder("Address 1", "Address 2")))
                .andExpect(jsonPath("$[*].phone", containsInAnyOrder("Phone 1", "Phone 2")))
                .andExpect(jsonPath("$[*].email", containsInAnyOrder("Email 1", "Email 2")))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void get() throws Exception {
        var request = ClientStub.generateClient();

        when(repository.getById(anyInt())).thenReturn(request);

        mockMvc.perform(getRequest("/clients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getFirstName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getLastName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getMiddleName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getAddress())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getEmail())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getPhone())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        var request = ClientStub.generateClientModify();
        request.setFirstName("Updated name");

        var expect = ClientStub.generateClient();
        expect.setFirstName("Updated name");

        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(putRequest("/clients/1", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getFirstName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getLastName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getMiddleName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getAddress())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getEmail())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getPhone())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        var request = ClientStub.generateClientModify();

        var expect = ClientStub.generateClient();

        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(postRequest("/clients", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getFirstName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getLastName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getMiddleName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getAddress())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getEmail())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getPhone())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        mockMvc.perform(deleteRequest("/clients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(repository, atLeast(1)).deleteById(1);
    }
}
