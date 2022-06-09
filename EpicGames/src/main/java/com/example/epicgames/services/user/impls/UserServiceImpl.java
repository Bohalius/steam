package com.example.epicgames.services.user.impls;

import com.example.epicgames.DTO.account.user.req.UserModify;
import com.example.epicgames.DTO.account.user.resp.UserDTO;
import com.example.epicgames.model.User;
import com.example.epicgames.repositories.UserRepository;
import com.example.epicgames.services.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository repository;

    @Override
    public Page<UserDTO> getAll(Integer page, Integer size) {
        Page<User> users = repository.findAll(PageRequest.of(page, size));

        var usersDTOs = users.get().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<UserDTO>(usersDTOs);
    }

    @Override
    public UserDTO get(Integer id) {
        return convertToDTO(repository.getById(id));
    }

    @Override
    public UserDTO create(UserModify entity) {

        return convertToDTO(repository.save(User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build())
        );
    }

    @Override
    public UserDTO update(UserModify entity) {

        return convertToDTO(repository.save(User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build())
        );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private UserDTO convertToDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .build();
    }
}
