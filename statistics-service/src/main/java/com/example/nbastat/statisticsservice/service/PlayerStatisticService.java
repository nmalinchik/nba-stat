package com.example.nbastat.statisticsservice.service;

import com.example.nbastat.statisticsservice.mapper.PlayerStatisticMapper;
import com.example.nbastat.statisticsservice.model.PlayerStatisticDto;
import com.example.nbastat.statisticsservice.repos.PlayerStatisticRepository;
import com.example.nbastat.statisticsservice.service.external.AnalyticClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlayerStatisticService {

    private final PlayerStatisticRepository playerStatisticRepository;
    private final AnalyticClient analyticClient;

    public PlayerStatisticDto getById(UUID id) {
        return playerStatisticRepository.findById(id)
                .map(PlayerStatisticMapper.INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Player statistic with id " + id + " not found"));
    }

    public List<PlayerStatisticDto> getPlayerStatistics(UUID playerId, Year season) {
        return playerStatisticRepository.findByPlayerIdAndSeason(playerId, season).stream()
                .map(PlayerStatisticMapper.INSTANCE::toDto)
                .toList();
    }

    public List<PlayerStatisticDto> getTeamStatistics(UUID teamId, Year season) {
        return playerStatisticRepository.findByTeamIdAndSeason(teamId, season).stream()
                .map(PlayerStatisticMapper.INSTANCE::toDto)
                .toList();
    }

    public PlayerStatisticDto save(PlayerStatisticDto playerStatisticDto) {
        var saved = playerStatisticRepository.save(PlayerStatisticMapper.INSTANCE.toEntity(playerStatisticDto));
        cleanCache(saved.getPlayerId(), saved.getTeamId(), saved.getSeason());
        return PlayerStatisticMapper.INSTANCE.toDto(saved);
    }

    public PlayerStatisticDto update(UUID id, PlayerStatisticDto playerStatisticDto) {
        var entity = playerStatisticRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player statistic with id " + id + " not found"));
        var updated = PlayerStatisticMapper.INSTANCE.updateEntity(entity, playerStatisticDto);
        var saved = playerStatisticRepository.save(updated);
        cleanCache(saved.getPlayerId(), saved.getTeamId(), saved.getSeason());
        return PlayerStatisticMapper.INSTANCE.toDto(saved);
    }

    public void delete(UUID id) {
        var statistic = playerStatisticRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player statistic with id " + id + " not found"));
        playerStatisticRepository.deleteById(id);
        cleanCache(statistic.getPlayerId(), statistic.getTeamId(), statistic.getSeason());
    }

    private void cleanCache(UUID playerId, UUID teamId, Year season) {
        analyticClient.clearPlayerStatisticCache(playerId, season);
        analyticClient.clearTeamStatisticCache(teamId, season);
    }

}
