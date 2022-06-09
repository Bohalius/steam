package com.example.epicgames.services.game.impls;

import com.example.epicgames.DTO.account.category.resp.CategoryDTO;
import com.example.epicgames.DTO.account.developer.resp.DeveloperDTO;
import com.example.epicgames.DTO.account.game.req.GameModify;
import com.example.epicgames.DTO.account.game.resp.GameDTO;
import com.example.epicgames.model.Category;
import com.example.epicgames.model.Developer;
import com.example.epicgames.model.Game;
import com.example.epicgames.repositories.GameRepository;
import com.example.epicgames.services.category.services.ICategoryService;
import com.example.epicgames.services.developers.services.IDeveloperService;
import com.example.epicgames.services.game.services.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements IGameService {
    private final GameRepository repository;
    private final IDeveloperService developerService;
    private final ICategoryService categoryService;

    @Override
    public Page<GameDTO> getAll(Integer page, Integer size) {
        Page<Game> games = repository.findAll(PageRequest.of(page, size));

        var gamesDTOs = games.get().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<GameDTO>(gamesDTOs);
    }

    @Override
    public GameDTO get(Integer id) {
        return convertToDTO(repository.getById(id));
    }



    @Override
    public GameDTO create(GameModify entity) {
        return convertToDTO(repository.save(
                Game.builder()
                                .developers(bindGameWithDevelopers(entity.getDevelopers()))
                                .categories(bindGameWithCategories(entity.getCategories()))
                                .price(entity.getPrice())
                                .name(entity.getName())
                                .publishingHouse(entity.getPublishingHouse())
                                .build()
                )
        );
    }

    @Override
    public GameDTO update(GameModify entity) {
        return convertToDTO(repository.save(
                       Game.builder()
                                .id(entity.getId())
                                .developers(bindGameWithDevelopers(entity.getDevelopers()))
                                .categories(bindGameWithCategories(entity.getCategories()))
                                .price(entity.getPrice())
                                .name(entity.getName())
                                .publishingHouse(entity.getPublishingHouse())
                                .build()
                )
        );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }


    @Override
    public GameDTO convertToDTO(Game entity) {
        return GameDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .developers(getGameDeveloperDTOs(entity.getDevelopers()))
                .categories(getBookCategoryDTOs(entity.getCategories()))
                .build();
    }

    @Override
    public String convertToDTOString(Integer gameId) {
        try {
            Game game = repository.getById(gameId);

            return convertToDTO(game).toString();
        } catch (Exception e) {
            return "-DELETED GAME-";
        }
    }


    public List<DeveloperDTO> getGameDeveloperDTOs(List<Developer> developers) {
        return developers.stream().map(
                item -> developerService.convertToDTO(item.getId())
        ).collect(Collectors.toList());
    }

    public List<CategoryDTO> getBookCategoryDTOs(List<Category> categories) {
        return categories.stream().map(
                item -> categoryService.convertToDTO(item.getId())
        ).collect(Collectors.toList());
    }

    public List<Developer> bindGameWithDevelopers(List<Integer> developerIds) {
        return developerIds.stream()
                .filter(
                        item -> {
                            try {
                                developerService.get(item);

                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                )
                .map(
                        item -> Developer.builder()
                                .id(item)
                                .build()
                )
                .collect(Collectors.toList());
    }

    public List<Category> bindGameWithCategories(List<Integer> categoryIds) {
        return categoryIds.stream()
                .filter(
                        item -> {
                            try {
                                categoryService.get(item);

                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                )
                .map(
                        item -> {
                            return Category.builder()
                                    .id(item)
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getCategoriesNamesByGameId(Integer gameId) {
        return categoryService.getCategoriesNamesByGameId(gameId);
    }
}
