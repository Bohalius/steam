package com.example.epicgames.controllers.rest;

import com.example.epicgames.DTO.account.game.req.GameModify;
import com.example.epicgames.DTO.account.game.resp.GameDTO;
import com.example.epicgames.services.game.services.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class GameController {
    private final IGameService gameService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<GameDTO>> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(gameService.getAll(page, size).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<GameDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(gameService.get(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GameDTO> update(@PathVariable Integer id, @RequestBody GameModify entity) {
        entity.setId(id);
        return ResponseEntity.ok(gameService.update(entity));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GameDTO> create(@RequestBody GameModify entity) {
        return ResponseEntity.ok(gameService.create(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        gameService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/categories")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<String>> getGameCategories(@PathVariable Integer id) {
        return ResponseEntity.ok(gameService.getCategoriesNamesByGameId(id));
    }
}
