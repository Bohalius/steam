package com.example.epicgames.services.order.services;

import com.example.epicgames.DTO.account.order.req.OrderCreate;
import com.example.epicgames.DTO.account.order.req.OrderUpdate;
import com.example.epicgames.DTO.account.order.resp.OrderDTO;
import org.springframework.data.domain.Page;

public interface IOrderService {
    Page<OrderDTO> getAll(Integer page, Integer size);

    OrderDTO get(Integer id);

    OrderDTO create(OrderCreate entity);

    OrderDTO update(OrderUpdate entity);

    void delete(Integer id);
}
