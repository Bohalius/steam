package com.example.epicgames.controllers.rest;

import com.example.epicgames.DTO.account.order.req.OrderCreate;
import com.example.epicgames.DTO.account.order.req.OrderUpdate;
import com.example.epicgames.DTO.account.order.resp.OrderDTO;
import com.example.epicgames.services.order.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<OrderDTO>> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(orderService.getAll(page, size).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<OrderDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> update(@PathVariable Integer id, @RequestBody OrderUpdate entity) {
        entity.setId(id);
        return ResponseEntity.ok(orderService.update(entity));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderCreate entity) {
        return ResponseEntity.ok(orderService.create(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
