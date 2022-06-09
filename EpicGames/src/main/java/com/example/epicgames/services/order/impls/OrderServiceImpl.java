package com.example.epicgames.services.order.impls;

import com.example.epicgames.DTO.account.order.req.OrderCreate;
import com.example.epicgames.DTO.account.order.req.OrderUpdate;
import com.example.epicgames.DTO.account.order.resp.OrderDTO;
import com.example.epicgames.model.Account;
import com.example.epicgames.model.Order;
import com.example.epicgames.repositories.OrderRepository;
import com.example.epicgames.services.account.services.IAccountService;
import com.example.epicgames.services.game.services.IGameService;
import com.example.epicgames.services.order.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository repository;
    private final IGameService gameService;
    private final IAccountService accountService;

    @Override
    public Page<OrderDTO> getAll(Integer page, Integer size) {
        Page<Order> orders = repository.findAll(PageRequest.of(page, size));

        var ordersDTOs = orders.get().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<OrderDTO>(ordersDTOs);
    }

    @Override
    public OrderDTO get(Integer id) {
        return convertToDTO(repository.getById(id));
    }

    @Override
    public OrderDTO create(OrderCreate entity) {

        return convertToDTO(
                repository.save(
                        Order.builder()
                                .totalSum(entity.getTotalSum())
                                .account(Account.builder()
                                        .id(entity.getAccountId())
                                        .build())
                                .address(entity.getAddress())
                                .orderDate(LocalDateTime.now())
                                .games(bindOrderWithGames(entity.getGamesId()))
                                .build()
                )
        );
    }

    @Override
    public OrderDTO update(OrderUpdate entity) {
        Order order = repository.getById(entity.getId());

        return convertToDTO(
                repository.save(
                        Order.builder()
                                .id(entity.getId())
                                .totalSum(entity.getTotalSum())
                                .address(entity.getAddress())
                                .orderDate(order.getOrderDate())
                                .games(order.getGames())
                                .account(order.getAccount())
                                .build()
                )
        );
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order entity) {
        List<String> games = Arrays.stream(entity.getGames())
                .map(gameService::convertToDTOString)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .totalSum(entity.getTotalSum())
                .address(entity.getAddress())
                .games(games)
                .account(accountService.convertToDTOString(entity.getAccount().getId()))
                .build();
    }

    private Integer[] bindOrderWithGames(List<Integer> gamesIds) {
        return gamesIds.stream()
                .filter(
                        item ->
                        {
                            try {
                                gameService.get(item);

                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                ).toArray(Integer[]::new);
    }

}
