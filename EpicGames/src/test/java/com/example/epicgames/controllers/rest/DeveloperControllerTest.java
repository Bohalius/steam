package com.example.epicgames.controllers.rest;

import com.example.epicgames.model.Developer;
import com.example.epicgames.repositories.DeveloperRepository;
import com.example.epicgames.stubs.DeveloperStub;
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
class DeveloperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeveloperRepository repository;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAll() throws Exception {
        var request = DeveloperStub.generateDevelopersList();

        when(repository.findAll(PageRequest.of(DeveloperStub.level, DeveloperStub.size)))
                .thenReturn(new PageImpl<Developer>(request));

        mockMvc.perform(getRequest("/developers?page=0&size=2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].firstName", containsInAnyOrder("FirstName", "FirstName")))
                .andExpect(jsonPath("$[*].middleName", containsInAnyOrder("MiddleName", "MiddleName")))
                .andExpect(jsonPath("$[*].lastName", containsInAnyOrder("LastName", "LastName")))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void get() throws Exception {
        var request = DeveloperStub.generateDeveloper();

        when(repository.getById(anyInt())).thenReturn(request);

        mockMvc.perform(getRequest("/developers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getFirstName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getLastName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getLastName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        var request = DeveloperStub.generateDeveloperModifyRequest();
        request.setFirstName("Updated name");

        var expect = DeveloperStub.generateDeveloper();
        expect.setFirstName("Updated name");

        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(putRequest("/authors/1", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getFirstName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getMiddleName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getLastName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        var request = DeveloperStub.generateDeveloperModifyRequest();

        var expect = DeveloperStub.generateDeveloper();

        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(postRequest("/developers", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getFirstName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getMiddleName())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getLastName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        mockMvc.perform(deleteRequest("/developers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(repository, atLeast(1)).deleteById(1);
    }
}
