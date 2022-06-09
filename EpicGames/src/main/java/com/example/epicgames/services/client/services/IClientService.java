package com.example.epicgames.services.client.services;

import com.example.epicgames.DTO.account.client.req.ClientModify;
import com.example.epicgames.DTO.account.client.resp.ClientDTO;
import org.springframework.data.domain.Page;

public interface IClientService {
    Page<ClientDTO> getAll(Integer page, Integer size);

    ClientDTO get(Integer id);

    ClientDTO create(ClientModify entity);

    ClientDTO update(ClientModify entity);

    void delete(Integer id);
}
