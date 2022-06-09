package com.example.epicgames.services.user.services;

import com.example.epicgames.DTO.account.user.req.UserModify;
import com.example.epicgames.DTO.account.user.resp.UserDTO;
import org.springframework.data.domain.Page;

public interface IUserService {
    Page<UserDTO> getAll(Integer page, Integer size);

    UserDTO get(Integer id);

    UserDTO create(UserModify entity);

    UserDTO update(UserModify entity);

    void delete(Integer id);
}
