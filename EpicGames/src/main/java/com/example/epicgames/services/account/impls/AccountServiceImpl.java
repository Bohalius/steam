package com.example.epicgames.services.account.impls;

import com.example.epicgames.DTO.account.client.req.ClientModify;
import com.example.epicgames.DTO.account.req.AccountCreate;
import com.example.epicgames.DTO.account.req.AccountUpdate;
import com.example.epicgames.DTO.account.resp.AccountDTO;
import com.example.epicgames.DTO.account.user.req.UserModify;
import com.example.epicgames.model.Account;
import com.example.epicgames.model.Client;
import com.example.epicgames.model.User;
import com.example.epicgames.repositories.AccountRepository;
import com.example.epicgames.services.account.services.IAccountService;
import com.example.epicgames.services.client.services.IClientService;
import com.example.epicgames.services.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository repository;
    private final IUserService userService;
    private final IClientService clientService;

    @Override
    public Page<AccountDTO> getAll(Integer page, Integer size) {
        Page<Account> accounts = repository.findAll(PageRequest.of(page, size));

        var accountDTOs = accounts.get().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<AccountDTO>(accountDTOs);
    }

    @Override
    public AccountDTO get(Integer id) {
        return convertToDTO(repository.getById(id));
    }


    @Override
    public AccountDTO create(AccountCreate entity) {
        UserModify newUser = UserModify.builder()
                .password(entity.getPassword())
                .username(entity.getLogin())
                .build();

        ClientModify newClient = ClientModify.builder()
                .phone(entity.getPhone())
                .firstName(entity.getFirstName())
                .middleName(" ")
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .address(" ")
                .build();

        return convertToDTO(
                repository.save(Account.builder()
                        .balance(entity.getBalance())
                        .user(User.builder()
                                .id(userService.create(newUser).getId())
                                .build())
                        .client(Client.builder()
                                .id(clientService.create(newClient).getId())
                                .build())
                        .isActive(entity.isActive())
                        .build())
        );
    }

    @Override
    public AccountDTO update(AccountUpdate entity) {
        Account account = repository.getById(entity.getId());

        return convertToDTO(repository.save(
                        Account.builder()
                                .isActive(entity.isActive())
                                .id(account.getId())
                                .balance(entity.getBalance())
                                .client(Client.builder()
                                        .id(account.getClient().getId())
                                        .build())
                                .user(User.builder()
                                        .id(account.getUser().getId())
                                        .build())
                                .build()
                )
        );
    }

    @Override
    public void delete(Integer id) {
        Account account = repository.getById(id);

        Integer userId = account.getUser().getId();
        Integer clientId = account.getClient().getId();

        repository.deleteById(id);
        userService.delete(userId);
        clientService.delete(clientId);
    }

    @Override
    public Client findClientByUserId(Integer id) {
        Account account = repository.findByUserId(id);

        return account == null ? null : account.getClient();
    }

    @Override
    public User findUserByClientId(Integer id) {
        Account account = repository.findByClientId(id);
        return account == null ? null : account.getUser();
    }

    @Override
    public String convertToDTOString(Integer accountId) {
        try {
            Account account = repository.getById(accountId);

            return convertToDTO(account).toString();
        } catch (Exception e) {
            return "-DELETED ACCOUNT-";
        }
    }

    public AccountDTO convertToDTO(Account entity) {
        return AccountDTO.builder()
                .id(entity.getId())
                .clientId(entity.getClient().getId())
                .userId(entity.getUser().getId())
                .balance(entity.getBalance())
                .isActive(entity.isActive())
                .build();
    }
}