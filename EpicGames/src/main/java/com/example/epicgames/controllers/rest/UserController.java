package com.example.epicgames.controllers.rest;

import com.example.epicgames.DTO.account.security.req.LoginRequest;
import com.example.epicgames.DTO.account.security.req.SignUpRequest;
import com.example.epicgames.DTO.account.user.req.UserModify;
import com.example.epicgames.DTO.account.user.resp.UserDTO;
import com.example.epicgames.model.Client;
import com.example.epicgames.security.AuthService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final IAccountService accountService;
    private final IClientService clientService;
    private final AuthService service;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticate( @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticateRequest(request));
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> createUser( @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(service.signUpUser(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<UserDTO>> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(userService.getAll(page, size).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserModify entity) {
        entity.setId(id);
        return ResponseEntity.ok(userService.update(entity));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> create(@RequestBody UserModify entity) {
        return ResponseEntity.ok(userService.create(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Client client = accountService.findClientByUserId(id);

        if (client != null) clientService.delete(client.getId());

        userService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
