package com.example.nbastat.playerservice.controllers;

import com.example.nbastat.playerservice.model.Player;
import com.example.nbastat.playerservice.model.PlayerDto;
import com.example.nbastat.playerservice.services.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable UUID id) {
        return ResponseEntity.ok(playerService.getPlayer(id));
    }

    @GetMapping("/short/{id}")
    public ResponseEntity<Player> getShortPlayer(@PathVariable UUID id) {
        return ResponseEntity.ok(playerService.getShortPlayer(id));
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<List<Player>> getTeamPlayers(@PathVariable UUID id) {
        return ResponseEntity.ok(playerService.getTeamPlayers(id));
    }

    @PostMapping
    public ResponseEntity<PlayerDto> savePlayer(@Valid @RequestBody PlayerDto player) {
        return ResponseEntity.ok(playerService.savePlayer(player));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable UUID id, @Valid @RequestBody PlayerDto playerDto) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

}
