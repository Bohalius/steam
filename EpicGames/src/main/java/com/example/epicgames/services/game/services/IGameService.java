package com.example.epicgames.services.game.services;

import com.example.epicgames.DTO.account.game.req.GameModify;
import com.example.epicgames.DTO.account.game.resp.GameDTO;
import com.example.epicgames.model.Game;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGameService {
    Page<GameDTO> getAll(Integer page, Integer size);

    GameDTO get(Integer id);

    GameDTO create(GameModify entity);

    GameDTO update(GameModify entity);

    void delete(Integer id);

    GameDTO convertToDTO(Game entity);

    String convertToDTOString(Integer gameId);

    List<String> getCategoriesNamesByGameId(Integer gameId);
}
