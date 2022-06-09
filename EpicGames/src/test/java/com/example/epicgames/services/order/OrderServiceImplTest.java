package com.example.epicgames.services.order;

import com.example.epicgames.model.Order;
import com.example.epicgames.repositories.OrderRepository;
import com.example.epicgames.services.account.impls.AccountServiceImpl;
import com.example.epicgames.services.game.impls.GameServiceImpl;
import com.example.epicgames.services.order.impls.OrderServiceImpl;
import com.example.epicgames.stubs.CategoryStub;
import com.example.epicgames.stubs.OrderStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private GameServiceImpl gameService;

    @Mock
    private AccountServiceImpl accountService;

    @BeforeEach
    void setup() {
        orderService = new OrderServiceImpl(orderRepository, gameService, accountService);
    }

    @Test
    void successfulGetAll() {
        var expect = OrderStub.generateOrdersList();

        when(orderRepository.findAll(PageRequest.of(OrderStub.page, OrderStub.size)))
                .thenReturn(new PageImpl<Order>(expect));

        when(accountService.convertToDTOString(any())).thenReturn("Account");

        var result = orderService.getAll(0, 2);

        assertAll(() -> {
            assertEquals(2L, result.getTotalElements());

            var res1 = result.getContent().get(0);
            var res2 = result.getContent().get(1);

            assertEquals(res1.getId(), expect.get(0).getId());
            assertEquals(res1.getAddress(), expect.get(0).getAddress());
            assertEquals(res1.getAccount(), accountService.convertToDTOString(expect.get(0).getAccount().getId()));
            assertEquals(res1.getOrderDate(), expect.get(0).getOrderDate());
            assertEquals(res1.getTotalSum(), expect.get(0).getTotalSum());
            assertEquals(res1.getGames().size(), expect.get(0).getGames().length);

            assertEquals(res2.getId(), expect.get(1).getId());
            assertEquals(res2.getAddress(), expect.get(1).getAddress());
            assertEquals(res2.getAccount(), accountService.convertToDTOString(expect.get(1).getAccount().getId()));
            assertEquals(res2.getOrderDate(), expect.get(1).getOrderDate());
            assertEquals(res2.getTotalSum(), expect.get(1).getTotalSum());
            assertEquals(res2.getGames().size(), expect.get(1).getGames().length);
        });
    }

    @Test
    void successfulGet() {
        var expect = OrderStub.generateOrder();

        when(orderRepository.getById(anyInt())).thenReturn(expect);
        when(accountService.convertToDTOString(any())).thenReturn("Account");

        var result = orderService.get(OrderStub.ID);

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getAddress(), result.getAddress());
            assertEquals(accountService.convertToDTOString(expect.getAccount().getId()), result.getAccount());
            assertEquals(expect.getOrderDate(), result.getOrderDate());
            assertEquals(expect.getTotalSum(), result.getTotalSum());
            assertEquals(expect.getGames().length, result.getGames().size());
        });
    }

    @Test
    void successfulCreate() {
        var expect = OrderStub.generateOrder();

        when(orderRepository.save(any())).thenReturn(expect);
        when(accountService.convertToDTOString(any())).thenReturn("Account");

        var result = orderService.create(OrderStub.generateOrderCreateRequest());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getAddress(), result.getAddress());
            assertEquals(accountService.convertToDTOString(expect.getAccount().getId()), result.getAccount());
            assertEquals(expect.getOrderDate(), result.getOrderDate());
            assertEquals(expect.getTotalSum(), result.getTotalSum());
            assertEquals(expect.getGames().length, result.getGames().size());
        });
    }

    @Test
    void successfulUpdate() {
        var expect = OrderStub.generateOrder();

        when(orderRepository.getById(any())).thenReturn(expect);
        when(orderRepository.save(any())).thenReturn(expect);

        var result = orderService.update(OrderStub.generateOrderUpdateRequest());

        assertAll(() -> {
            assertEquals(expect.getId(), result.getId());
            assertEquals(expect.getAddress(), result.getAddress());
            assertEquals(expect.getTotalSum(), result.getTotalSum());
        });
    }

    @Test
    void successfulDelete() {
        var deleteCaptor = ArgumentCaptor.forClass(Integer.class);

        orderService.delete(OrderStub.ID);
        verify(orderRepository, atLeast(1)).deleteById(deleteCaptor.capture());
        assertEquals(CategoryStub.ID, deleteCaptor.getValue());
    }

}
