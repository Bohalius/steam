package com.example.epicgames.controllers.rest;

import com.example.epicgames.model.Game;
import com.example.epicgames.repositories.GameRepository;
import com.example.epicgames.services.category.services.ICategoryService;
import com.example.epicgames.services.developers.services.IDeveloperService;
import com.example.epicgames.stubs.GameStub;
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

import java.util.ArrayList;

import static com.example.epicgames.configuration.ServletRequestConfig.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository repository;

    @MockBean
    private IDeveloperService developerService;

    @MockBean
    private ICategoryService categoryService;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAll() throws Exception {
        var request = GameStub.generateGamesList();

        when(repository.findAll(PageRequest.of(GameStub.level, GameStub.size)))
                .thenReturn(new PageImpl<Game>(request));

        mockMvc.perform(getRequest("/games?page=0&size=2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].price", containsInAnyOrder(10, 10)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Name", "Name")))
                .andExpect(jsonPath("$[*].authors[*]", hasSize(0)))
                .andExpect(jsonPath("$[*].categories[*]", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void get() throws Exception {
        var request = GameStub.generateGame();

        when(repository.getById(anyInt())).thenReturn(request);

        mockMvc.perform(getRequest("/games/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getPrice().toString())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(request.getName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        var request = GameStub.generateGameModifyRequest();
        request.setName("New name");

        var expect = GameStub.generateGame();
        expect.setName("New name");

        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(putRequest("/games/1", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getPrice().toString())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getName())))
                .andExpect(jsonPath("$[*].developers[*]", empty()))
                .andExpect(jsonPath("$[*].categories[*]", empty()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        var request = GameStub.generateGameModifyRequest();

        var expect = GameStub.generateGame();

        when(repository.save(any())).thenReturn(expect);

        mockMvc.perform(postRequest("/games", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getPrice().toString())))
                .andExpect(MockMvcResultMatchers.content().string(containsString(expect.getName())))
                .andExpect(jsonPath("$[*].developers[*]", empty()))
                .andExpect(jsonPath("$[*].categories[*]", empty()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete() throws Exception {
        mockMvc.perform(deleteRequest("/games/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(repository, atLeast(1)).deleteById(1);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getBookCategories() throws Exception {
        when(categoryService.getCategoriesNamesByGameId(anyInt())).thenReturn(new ArrayList<String>());

        mockMvc.perform(getRequest("/games/1/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*]", empty()));
    }

}
