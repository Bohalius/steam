package com.example.epicgames.controllers.rest;

import com.example.epicgames.DTO.account.req.AccountCreate;
import com.example.epicgames.DTO.account.req.AccountUpdate;
import com.example.epicgames.DTO.account.resp.AccountDTO;
import com.example.epicgames.services.account.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AccountDTO>> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(accountService.getAll(page, size).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AccountDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.get(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> update(@PathVariable Integer id, @RequestBody AccountUpdate entity) {
        entity.setId(id);
        return ResponseEntity.ok(accountService.update(entity));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> create(@RequestBody AccountCreate entity) {
        return ResponseEntity.ok(accountService.create(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        accountService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
