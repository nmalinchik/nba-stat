package com.example.nbastat.statisticsservice.controllers;

import com.example.nbastat.statisticsservice.model.PlayerStatisticDto;
import com.example.nbastat.statisticsservice.service.PlayerStatisticService;
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

import java.time.Year;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistic")
public class PlayerStatisticController {

    private final PlayerStatisticService playerStatisticService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerStatisticDto> getPlayerStatistic(@PathVariable UUID id) {
        return ResponseEntity.ok(playerStatisticService.getById(id));
    }

    @GetMapping("/player/{id}/{season}")
    public ResponseEntity<List<PlayerStatisticDto>> getPlayerStatisticForSeason(@PathVariable UUID id, @PathVariable Year season) {
        return ResponseEntity.ok(playerStatisticService.getPlayerStatistics(id, season));
    }

    @GetMapping("/team/{id}/{season}")
    public ResponseEntity<List<PlayerStatisticDto>> getTeamStatisticForSeason(@PathVariable UUID id, @PathVariable Year season) {
        return ResponseEntity.ok(playerStatisticService.getTeamStatistics(id, season));
    }

    @PostMapping
    public ResponseEntity<PlayerStatisticDto> savePlayerStatistic(@RequestBody @Valid PlayerStatisticDto playerStatisticDto) {
        return ResponseEntity.ok(playerStatisticService.save(playerStatisticDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerStatisticDto> updatePlayerStatistic(@PathVariable UUID id, @RequestBody @Valid PlayerStatisticDto playerStatisticDto) {
        return ResponseEntity.ok(playerStatisticService.update(id, playerStatisticDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlayerStatisticDto> deletePlayerStatistic(@PathVariable UUID id) {
        playerStatisticService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
