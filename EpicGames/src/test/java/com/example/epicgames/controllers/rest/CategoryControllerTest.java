package com.example.epicgames.controllers.rest;

import com.example.epicgames.model.Category;
import com.example.epicgames.repositories.CategoryRepository;
import com.example.epicgames.stubs.CategoryStub;
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
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAll() throws Exception {
        var request = CategoryStub.generateCategoriesList();

        when(categoryRepository.findAll(PageRequest.of(CategoryStub.level, CategoryStub.size)))
                .thenReturn(new PageImpl<Category>(request));

        mockMvc.perform(getRequest("/categories?page=0&size=2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Name 1", "Name 2")))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void get() throws Exception {
        var request = CategoryStub.generateCategory();

        when(categoryRepository.getById(anyInt())).thenReturn(request);

        mockMvc.perform(getRequest("/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        var request = CategoryStub.generateDTO();
        request.setName("Updated name");

        var expect = CategoryStub.generateCategory();
        expect.setName("Updated name");

        when(categoryRepository.save(any())).thenReturn(expect);

        mockMvc.perform(putRequest("/categories/1", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        var request = CategoryStub.generateDTO();
        var expect = CategoryStub.generateCategory();

        when(categoryRepository.save(any())).thenReturn(expect);

        mockMvc.perform(postRequest("/categories", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        mockMvc.perform(deleteRequest("/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(categoryRepository, atLeast(1)).deleteById(1);
    }
}
