package com.example.epicgames.services.developers.services;

import com.example.epicgames.DTO.account.developer.req.DeveloperModify;
import com.example.epicgames.DTO.account.developer.resp.DeveloperDTO;
import com.example.epicgames.model.Developer;
import org.springframework.data.domain.Page;

public interface IDeveloperService {
    Page<DeveloperDTO> getAll(Integer level, Integer size);

    DeveloperDTO get(Integer id);

    DeveloperDTO create(DeveloperModify entity);

    DeveloperDTO update(DeveloperModify entity);

    void delete(Integer id);

    DeveloperDTO convertToDTO(Developer entity);

    DeveloperDTO convertToDTO(Integer authorId);
}
