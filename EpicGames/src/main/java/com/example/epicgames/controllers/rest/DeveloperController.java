package com.example.epicgames.controllers.rest;

import com.example.epicgames.DTO.account.developer.req.DeveloperModify;
import com.example.epicgames.DTO.account.developer.resp.DeveloperDTO;
import com.example.epicgames.services.developers.services.IDeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class DeveloperController {
    private final IDeveloperService developerService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<DeveloperDTO>> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(developerService.getAll(page, size).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<DeveloperDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(developerService.get(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeveloperDTO> update(@PathVariable Integer id, @RequestBody DeveloperModify entity) {
        entity.setId(id);
        return ResponseEntity.ok(developerService.update(entity));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeveloperDTO> create(@RequestBody DeveloperModify entity) {
        return ResponseEntity.ok(developerService.create(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        developerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
