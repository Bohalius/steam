package com.example.epicgames.services.account.services;

import com.example.epicgames.DTO.account.req.AccountCreate;
import com.example.epicgames.DTO.account.req.AccountUpdate;
import com.example.epicgames.DTO.account.resp.AccountDTO;
import com.example.epicgames.model.Client;
import com.example.epicgames.model.User;
import org.springframework.data.domain.Page;

public interface IAccountService {
    Page<AccountDTO> getAll(Integer page, Integer size);

    AccountDTO get(Integer id);

    AccountDTO create(AccountCreate entity);

    AccountDTO update(AccountUpdate entity);

    void delete(Integer id);

    Client findClientByUserId(Integer id);

    User findUserByClientId(Integer id);

    String convertToDTOString(Integer accountId);
}
