package com.example.epicgames.services.client.impls;

import com.example.epicgames.DTO.account.client.req.ClientModify;
import com.example.epicgames.DTO.account.client.resp.ClientDTO;
import com.example.epicgames.model.Client;
import com.example.epicgames.repositories.ClientRepository;
import com.example.epicgames.services.client.services.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final ClientRepository repository;

    @Override
    public Page<ClientDTO> getAll(Integer page, Integer size) {
        Page<Client> clients = repository.findAll(PageRequest.of(page, size));

        var clientsDTOs = clients.get().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<ClientDTO>(clientsDTOs);
    }

    @Override
    public ClientDTO get(Integer id) {
        return convertToDTO(repository.getById(id));
    }

    @Override
    public ClientDTO create(ClientModify entity) {

        return convertToDTO(repository.save(Client.builder()
                .address(entity.getAddress())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build())
        );
    }

    @Override
    public ClientDTO update(ClientModify entity) {
        return convertToDTO(repository.save(Client.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build())
        );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private ClientDTO convertToDTO(Client entity) {
        return ClientDTO.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .phone(entity.getPhone())
                .build();
    }
}
