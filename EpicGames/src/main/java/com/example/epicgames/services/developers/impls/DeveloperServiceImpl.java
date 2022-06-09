package com.example.epicgames.services.developers.impls;

import com.example.epicgames.DTO.account.developer.req.DeveloperModify;
import com.example.epicgames.DTO.account.developer.resp.DeveloperDTO;
import com.example.epicgames.model.Developer;
import com.example.epicgames.repositories.DeveloperRepository;
import com.example.epicgames.services.developers.services.IDeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeveloperServiceImpl implements IDeveloperService {
    private final DeveloperRepository repository;

    @Override
    public Page<DeveloperDTO> getAll(Integer level, Integer size) {
        Page<Developer> developers = repository.findAll(PageRequest.of(level, size));

        var developerDTOs = developers.get().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<DeveloperDTO>(developerDTOs);
    }

    @Override
    public DeveloperDTO get(Integer id) {
        return convertToDTO(repository.getById(id));
    }


    @Override
    public DeveloperDTO create(DeveloperModify entity) {
        return convertToDTO(repository.save(
                Developer.builder()
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .middleName(entity.getMiddleName())
                        .info(entity.getInfo())
                        .build()
        ));
    }

    @Override
    public DeveloperDTO update(DeveloperModify entity) {
        return convertToDTO(repository.save(
                Developer.builder()
                        .id(entity.getId())
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .middleName(entity.getMiddleName())
                        .info(entity.getInfo())
                        .build())
        );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }


    @Override
    public DeveloperDTO convertToDTO(Developer entity) {
        return DeveloperDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .build();
    }

    @Override
    public DeveloperDTO convertToDTO(Integer developerId) {
        Developer developer = repository.getById(developerId);

        return DeveloperDTO.builder()
                .middleName(developer.getMiddleName())
                .lastName(developer.getLastName())
                .firstName(developer.getFirstName())
                .id(developerId)
                .build();
    }
}
