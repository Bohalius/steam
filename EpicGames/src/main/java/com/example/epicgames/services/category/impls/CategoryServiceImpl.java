package com.example.epicgames.services.category.impls;

import com.example.epicgames.DTO.account.category.resp.CategoryDTO;
import com.example.epicgames.model.Category;
import com.example.epicgames.repositories.CategoryRepository;
import com.example.epicgames.services.category.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository repository;

    @Override
    public Page<CategoryDTO> getAll(Integer page, Integer size) {
        Page<Category> categories = repository.findAll(PageRequest.of(page, size));

        var categoryDTOs = categories.get().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<CategoryDTO>(categoryDTOs);
    }

    @Override
    public CategoryDTO get(Integer id) {
        return convertToDTO(repository.getById(id));
    }

    @Override
    public CategoryDTO create(CategoryDTO entity) {
        return convertToDTO(repository.save(
                Category.builder()
                        .name(entity.getName())
                        .build())
        );
    }

    @Override
    public CategoryDTO update(CategoryDTO entity) {
        return convertToDTO(repository.save(
                Category.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .build()
        ));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public CategoryDTO convertToDTO(Category entity) {
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public CategoryDTO convertToDTO(Integer categoryId) {
        Category category = repository.getById(categoryId);

        return CategoryDTO.builder()
                .name(category.getName())
                .id(categoryId)
                .build();
    }

    @Override
    public List<String> getCategoriesNamesByGameId(Integer gameId) {
        Optional<List<Category>> list = repository.getGameCategoriesNames(gameId);

        return list.map(categories -> categories.stream().map(Category::getName).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
