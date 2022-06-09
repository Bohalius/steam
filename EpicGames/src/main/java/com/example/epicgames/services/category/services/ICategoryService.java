package com.example.epicgames.services.category.services;

import com.example.epicgames.DTO.account.category.resp.CategoryDTO;
import com.example.epicgames.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    Page<CategoryDTO> getAll(Integer page, Integer size);

    CategoryDTO get(Integer id);

    CategoryDTO create(CategoryDTO entity);

    CategoryDTO update(CategoryDTO entity);

    void delete(Integer id);

    CategoryDTO convertToDTO(Category entity);

    CategoryDTO convertToDTO(Integer categoryId);

    List<String> getCategoriesNamesByGameId(Integer gameId);
}
