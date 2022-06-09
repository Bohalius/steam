package com.example.epicgames.controllers.rest;

import com.example.epicgames.DTO.account.client.req.ClientModify;
import com.example.epicgames.DTO.account.client.resp.ClientDTO;
import com.example.epicgames.model.User;
import com.example.epicgames.services.account.services.IAccountService;
import com.example.epicgames.services.client.services.IClientService;
import com.example.epicgames.services.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService clientService;
    private final IAccountService accountService;
    private final IUserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ClientDTO>> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(clientService.getAll(page, size).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ClientDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.get(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientDTO> update(@PathVariable Integer id, @RequestBody ClientModify entity) {
        return ResponseEntity.ok(clientService.update(entity));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientModify entity) {
        return ResponseEntity.ok(clientService.create(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        User user = accountService.findUserByClientId(id);

        if (user != null) userService.delete(user.getId());

        clientService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
