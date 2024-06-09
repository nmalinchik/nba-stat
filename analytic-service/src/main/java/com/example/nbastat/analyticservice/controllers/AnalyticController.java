package com.example.nbastat.analyticservice.controllers;

import com.example.nbastat.analyticservice.model.dto.PlayerStatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.TeamStatisticAvgDto;
import com.example.nbastat.analyticservice.service.AnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analytic")
public class AnalyticController {

    private final AnalyticService analyticService;

    @GetMapping("/player/{id}/{season}")
    public ResponseEntity<PlayerStatisticAvgDto> getPlayerStatistic(@PathVariable UUID id, @PathVariable Year season) {
        return ResponseEntity.ok(analyticService.getAveragePlayerStatistic(id, season));
    }

    @GetMapping("/team/{id}/{season}")
    public ResponseEntity<TeamStatisticAvgDto> getTeamStatistic(@PathVariable UUID id, @PathVariable Year season) {
        return ResponseEntity.ok(analyticService.getAverageTeamStatistic(id, season));
    }

    @DeleteMapping("/player/{id}/{season}")
    public ResponseEntity<Void> clearPlayerStatisticCache(@PathVariable UUID id, @PathVariable Year season) {
        analyticService.clearPlayerStatisticsCache(id, season);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/team/{id}/{season}")
    public ResponseEntity<Void> clearTeamStatisticCache(@PathVariable UUID id, @PathVariable Year season) {
        analyticService.clearTeamStatisticsCache(id, season);
        return ResponseEntity.noContent().build();
    }
}
